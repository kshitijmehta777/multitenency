package com.ms.multi_tenency_db_per_tenent.api;

import com.ms.multi_tenency_db_per_tenent.model.JwtRequestDTO;
import com.ms.multi_tenency_db_per_tenent.model.JwtResponseDTO;
import com.ms.multi_tenency_db_per_tenent.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenApis {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/generate")
    public JwtResponseDTO generateToken(@RequestBody JwtRequestDTO jwtRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequestDTO.getUsername(), jwtRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return new JwtResponseDTO(jwtService.GenerateToken(jwtRequestDTO.getUsername()));
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }


}
