package com.samuel.github;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author slemagonzalez
 */
public class GitFlow {
    
    private static Repository repository;

    /**
     *
     * @param directorio
     * @throws IOException
     * Guarda el repositorio seleccionado para posteriormente trabajar con el.
     */
    public static void setRepository(File directorio) throws IOException {
        
        
        GitFlow.repository = Git.open(new File(directorio.getAbsolutePath() + "/.git")).checkout().getRepository();
    }

    /**
     *
     * @return
     * Devuelve el repositorio guardado para trabajar con el
     */
    public static Repository getRepository() {
        return repository;
    }

    /**
     * Inicializa el repositorio seleccionado.
     */
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
    
    /**
     *
     * @param text
     * Hacer un commit del repositorio seleccionado. Pide un texto como descripción del commit.
     */
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
    
    /**
     *
     * @param httpURL
     * @param username
     * @param password
     * @throws URISyntaxException
     * Permite subir el repositorio a github. Pide la URL, nombre y contraseña de la cuenta con la que se subira.
     */
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
    
    /**
     *
     * @param repositoryURL
     * @param directorio
     * Permite clonar un repositorio de github. Pide la url del repositorio y el directorio donde se guardara.
     */
    public static void clonar(String repositoryURL, File directorio) {
        try {
            Git.cloneRepository()
                    .setURI(repositoryURL)
                    .setDirectory(directorio)
                    .call();
            JOptionPane.showMessageDialog(null, "Repositorio clonado correctamente.");
            
        } catch (GitAPIException ex) {
            
            JOptionPane.showMessageDialog(null, "Ha habido un error al clonar el Repositorio.");
        }
    }
}
