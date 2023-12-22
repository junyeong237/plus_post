package com.example.plus_assignment.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class S3util {

        private final AmazonS3 amazonS3;

        @Value("${cloud.aws.s3.bucket}")
        private String bucket;


    @Getter
    @RequiredArgsConstructor
    public enum ImagePath {
        PROFILE("profile/"), MENU("menu/");

        private final String path;
    }
        private final static String IMAGE_JPG = "image/jpeg";
        private final static String IMAGE_PNG = "image/png";

        public String saveFile(MultipartFile multipartFile) throws IOException {
            String originalFilename = multipartFile.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
            return amazonS3.getUrl(bucket, originalFilename).toString();
        }

    public String uploadImage(MultipartFile multipartFile, ImagePath imagePath) throws IOException {
        if (!isImageFile(multipartFile)) {
            //throw new FileTypeNotAllowedException(AwsS3ErrorCode.FILE_NOT_ALLOW);
            throw new IllegalArgumentException("이미지파일이 아닙니다.");
        }

        String fileName = UUID.randomUUID().toString();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, imagePath.getPath() + fileName,
            multipartFile.getInputStream(), metadata);

        return fileName;
    }

    private boolean isImageFile(MultipartFile multipartFile) {
        return Objects.equals(multipartFile.getContentType(), IMAGE_JPG)
            || Objects.equals(multipartFile.getContentType(), IMAGE_PNG);
    }

    public String getImagePath(String fileName, ImagePath imagePath) {
        return amazonS3.getUrl(bucket, imagePath.getPath() + fileName).toString();
    }



}
