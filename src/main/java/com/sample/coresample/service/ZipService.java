package com.sample.coresample.service;

import com.sample.coresample.constant.path.RenderPath;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ZipService {

    public Boolean makeDirToZip(String dirPath) {
        File dir = new File(dirPath);
        File output = new File(RenderPath.OUTPUT_PATH.getPath()+"test.zip");

        ZipUtil.pack(dir,output);
        return true;
    }


}