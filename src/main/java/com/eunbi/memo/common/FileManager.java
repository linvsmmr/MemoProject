package com.eunbi.memo.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {


    public final static String FILE_UPLOAD_PATH = "C:\\EUNBILEE\\spring\\upload\\memo";

    // 파일을 전달 받아, 정해진 경로에 저장하고 해당 파일을 클라이언트가 접근할 수 있는 url 경로 리턴
    public static String saveFile(long userId, MultipartFile file) {

        if (file == null) {
            return null;
        }
        // 원본 파일 이름 그대로 저장
        // 디렉토리로 구분해서 파일 저장
        // 디렉터리 이름 : 사용자 정보 + 시간 정보
        // UNIX TIME : 1970년 1월 1일 0시 0분 0초 이후로 흐른 시간 (millisecond)

        String directoryName = "/" + userId + "_" + System.currentTimeMillis();

        // 디렉터리 생성
        String directoryPath = FILE_UPLOAD_PATH + directoryName;

        File directory = new File(directoryPath);

        if(!directory.mkdir()) {
            return null;
        }

        // 파일 저장
        String filePath = directoryPath + "/" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();

            Path path = Paths.get(filePath);
            Files.write(path, bytes);

        } catch (IOException e) {
            return null;
        }

        return "/images" + directoryName + "/" + file.getOriginalFilename();


    }


    public static boolean removeFile(String imagePath) {

        if (imagePath == null) {
            return false;
        }

        String fullFilePath = FILE_UPLOAD_PATH + imagePath.replace("/images","");

        Path path = Paths.get(fullFilePath);
        Path directoryPath =  path.getParent();


        try {
            Files.delete(path);
            Files.delete(directoryPath);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
