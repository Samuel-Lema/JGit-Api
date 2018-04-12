package com.samuel.github;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitFlow {
    
    private static Repository repository;

    public static void setRepository(File directorio) throws IOException {
        
        
        GitFlow.repository = Git.open(new File(directorio.getAbsolutePath() + "/.git")).checkout().getRepository();
    }

    public static Repository getRepository() {
        return repository;
    }

}
