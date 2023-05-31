package com.sample.coresample.controller;


import com.sample.coresample.constant.path.RenderPath;
import com.sample.coresample.dto.ModelRequestDto;
import com.sample.coresample.dto.ModelResponseDto;
import com.sample.coresample.service.BlenderService;
import com.sample.coresample.service.ZipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/blender")
@Validated
public class BlenderController {

    final BlenderService blenderService;
    final ZipService zipService;

    @GetMapping("/gltf")
    public ModelAndView gltf() {
        ModelResponseDto response = ModelResponseDto.builder()
                .fileName("scene")
                .isBool(false)
                .extension(".gltf")
                .build();

        return new ModelAndView(
                "blender/home"
                , Map.of(
                "response", response
        )
        );
    }

    @GetMapping("/obj")
    public ModelAndView obj() {
        ModelResponseDto response = ModelResponseDto.builder()
                .fileName("SM_gimhae_street_e_equip_a_02")
                .isBool(false)
                .extension(".obj")
                .build();

        return new ModelAndView(
                "blender/home"
                , Map.of(
                "response", response
        )
        );
    }

    @GetMapping("/fbx")
    public ModelAndView fbx() {
        ModelResponseDto response = ModelResponseDto.builder()
                .fileName("SM_gimhae_street_e_equip_a_02")
                .isBool(false)
                .extension(".fbx")
                .build();

        return new ModelAndView(
                "blender/home"
                , Map.of(
                "response", response
        )
        );
    }

    @GetMapping()
    public ModelAndView home(
    ) {
        ModelResponseDto response = ModelResponseDto.builder()
//                .fileName("test_sample")
                .fileName("SM_gimhae_street_e_equip_a_01")
                .isBool(false)
                .extension(".gltf")
                .build();

        return new ModelAndView(
                "blender/home"
                , Map.of(
                "response", response
        )
        );
    }

    @PostMapping
    public ModelAndView callBlender(
            @ModelAttribute ModelRequestDto requestDto
    ) {
        ModelResponseDto modelResponseDto = blenderService.generateModel(requestDto);

        return new ModelAndView(
                "blender/home"
                , Map.of(
                "response", modelResponseDto
        )
        );

    }

    @PostMapping("/api")
    public ResponseEntity callBlenderAPI(
            @RequestBody ModelRequestDto requestDto
    ) {
        ModelResponseDto modelResponseDto = blenderService.generateModel(requestDto);
        return ResponseEntity.ok().body(modelResponseDto);
    }


    @PostMapping("/api/upload")
    public ResponseEntity uploadPathFile(
            @RequestBody Map<String, String> filePath
    ) {
        System.out.println("filePath :: " + filePath.get("filePath"));
        String fileUrl = blenderService.uploadPathFile(filePath.get("filePath"), filePath.get("dir"));
        return ResponseEntity.ok(Map.of("url", fileUrl));
    }


    @GetMapping("/api/zip")
    public ResponseEntity makeDirToZip() {
        Boolean aBoolean = zipService.makeDirToZip(RenderPath.OUTPUT_PATH.getPath());
        if (aBoolean) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
