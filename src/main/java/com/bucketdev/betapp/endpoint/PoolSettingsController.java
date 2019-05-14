package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.PoolSettingsDTO;
import com.bucketdev.betapp.service.PoolSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/poolSettings")
public class PoolSettingsController {

    @Autowired
    private PoolSettingsService service;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<PoolSettingsDTO> upsert(@RequestBody PoolSettingsDTO dto) {
        return new ResponseEntity<>(service.upsert(dto), HttpStatus.CREATED);
    }

}
