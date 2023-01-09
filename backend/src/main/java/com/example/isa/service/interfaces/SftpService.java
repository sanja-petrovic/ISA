package com.example.isa.service.interfaces;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.stereotype.Service;

public interface SftpService {
    void download(String remoteFilePath) throws JSchException, SftpException;
}
