/*
 * MIT License
 *
 * Copyright (c) 2019. JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jcalvopinam.api;

import com.jcalvopinam.core.domain.AttachedFile;
import com.jcalvopinam.core.service.AttachedFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Juan Calvopina M. <juan.calvopina@gmail.com>
 */
@RestController
@Slf4j
public class AttachedFileController {

    private static final String DOWNLOAD_FILE_URI = "/downloadFile/";

    private AttachedFileService attachedFileService;

    @Autowired
    public AttachedFileController(final AttachedFileService attachedFileService) {
        this.attachedFileService = attachedFileService;
    }

    @PostMapping("/attach_file")
    public AttachedFile uploadFile(@RequestParam("file") MultipartFile file) {
        final String fileName = attachedFileService.storeFile(file);

        final String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                                  .path(DOWNLOAD_FILE_URI)
                                                                  .path(fileName)
                                                                  .toUriString();

        return new AttachedFile(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/attach_multiple_files")
    public List<AttachedFile> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                     .map(this::uploadFile)
                     .collect(Collectors.toList());
    }

    @GetMapping(DOWNLOAD_FILE_URI + "{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        final Resource resource = attachedFileService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            final String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            contentType = mimeType == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mimeType;

        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(contentType))
                             .header(HttpHeaders.CONTENT_DISPOSITION,
                                     "attachment; filename=\"" + resource.getFilename() + "\"")
                             .body(resource);
    }

}