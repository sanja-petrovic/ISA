package com.example.isa.service.implementation;

import com.example.isa.service.interfaces.SftpService;
import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.jcraft.jsch.JSch.setConfig;

@Service
public class SftpServiceImpl implements SftpService {

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession("isa", "localhost");
        jschSession.setPassword("isa");
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public void download(String remoteFilePath) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        channelSftp.get(remoteFilePath,"src\\main\\resources\\downloaded");
        channelSftp.exit();
    }
}
