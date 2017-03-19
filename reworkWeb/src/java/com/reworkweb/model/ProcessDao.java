package com.reworkweb.model;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.reworkweb.entities.ProcessRework;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mchamparini
 */
@Service
public class ProcessDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackFor = {ServicioException.class})
    /**
     * Get process list*
     */
    public List<ProcessRework> getAllProcessToRun() throws SecurityException {
        String sql = "select p from ProcessRework p";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }

    /**
     * Get process list by path*
     */
    public ProcessRework findProcess(String pathProcess) {
        //String sql= "select p from ProcessRework p where p.process_name := pathProcess";         
        // Query query = em.createQuery(sql); 
        Query query = em.createNamedQuery("ProcessRework.findByProcessName");
        query.setParameter("processName", pathProcess);
        return (ProcessRework) query.getSingleResult();
    }
//  
    /**
     * Delete process by path *
     */
    public void delete(String pathProcess) {
        em.remove(findProcess(pathProcess));
    }

    /**
     * Execute remote script*
     */
    public List<String> executeRemoteScript(String pathScript) {
        String USERNAME = "calidad"; // username for remote host
        String PASSWORD = "Calidad2016"; // password of the remote host
        String host = "10.92.56.198"; // remote host address
        List<String> result = new ArrayList<String>();
        int port = 22;
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(USERNAME, host, port);

            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(PASSWORD);


            session.connect();
            //ChannelExec channelexec2 = (ChannelExec) session2.openChannel("exec");
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand("sh " + pathScript);
            channelExec.connect();
         
            channelExec.disconnect();
            session.disconnect();

        } catch (JSchException e) {

        }
        return result;
    }

    public List<String> executeRemoteLS(String command) {
        List<String> result = new ArrayList<String>();
        String USERNAME = "calidad"; // username for remote host
        String PASSWORD = "Calidad2016"; // password of the remote host
        String host = "10.92.56.198"; // remote host address
        int port = 22;
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(USERNAME, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(PASSWORD);
            session.connect();
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            InputStream in = channelExec.getInputStream();
            channelExec.setCommand("sh " + command);
            channelExec.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            int exitStatus = channelExec.getExitStatus();
            channelExec.disconnect();
            session.disconnect();
        } catch (JSchException | IOException e) {

        }
        return result;
    }
}
