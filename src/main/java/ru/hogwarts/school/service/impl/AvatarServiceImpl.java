package ru.hogwarts.school.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepositories;
import ru.hogwarts.school.repositories.StudentRepositories;
import ru.hogwarts.school.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    private final AvatarRepositories avatarRepository;
    private final StudentRepositories studentRepository;

    public AvatarServiceImpl(AvatarRepositories avatarRepository, StudentRepositories studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method uploadAvatar for studentId = {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student not found with id = {}", studentId);
                    return new IllegalArgumentException("Student not found with id: " + studentId);
                });

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
            logger.debug("Avatar file written to path: {}", filePath);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(() -> {
            logger.debug("Creating new avatar record for studentId = {}", studentId);
            return new Avatar();
        });
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
        logger.info("Avatar saved for studentId = {}", studentId);
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method findAvatar for studentId = {}", studentId);
        return avatarRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    logger.warn("Avatar not found for studentId = {}", studentId);
                    return new NoSuchElementException("Avatar not found for studentId: " + studentId);
                });
    }

    private String getExtension(String fileName) {
        logger.debug("Extracting extension from filename: {}", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public Page<Avatar> getAvatars(int page, int size) {
        logger.info("Was invoked method getAvatars with page = {} and size = {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return avatarRepository.findAllAvatars(pageable);
    }
}
