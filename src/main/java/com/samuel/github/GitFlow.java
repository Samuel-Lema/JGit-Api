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

    public static void initRepository() {
        
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        
        try {
            repositoryBuilder.setGitDir(repository.getDirectory().getCanonicalFile())
                    .readEnvironment()
                    .findGitDir()
                    .setMustExist(true)
                    .build();
            JOptionPane.showMessageDialog(null, "El Repositorio ha sido inicializado correctamente.");
            
        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(null, "Ha habido un error al inicializar el Repositorio.");
        }
    }
    
    public static void commitRepository(String text) {
        
        Git git = new Git(repository);
            
        try {
            git.commit()
                    .setMessage(text)
                    .call();
            JOptionPane.showMessageDialog(null, "El Repositorio ha añadido el commit correctamente.");
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha habido un error al hacerle commit al Repositorio.");
        }
    }
    
    public static void pushRepository(String httpURL, String username, String password) throws URISyntaxException {

        try {
            Git git = new Git(repository);
            RemoteAddCommand remoteAddCommand = git.remoteAdd();
            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish(httpURL));
            remoteAddCommand.call();
            
            // Pushea y introduce los datos del usuario de git
            
            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            pushCommand.call();
            
        } catch (GitAPIException ex) {}
    }
}
