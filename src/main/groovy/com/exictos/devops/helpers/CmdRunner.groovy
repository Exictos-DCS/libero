package com.exictos.devops.helpers

import ch.qos.logback.classic.Logger

/**
 * A systems commands runner wrapper.
 */
class CmdRunner {

    protected static final Logger log = LiberoLogger.getLogger()

    /**
     * Runs a bash command and returns its exit code
     * @param command
     * @return script exit code
     */
    static int run(String command)
    {
        ProcessBuilder builder = new ProcessBuilder(command.split(' '))
        builder.redirectErrorStream(true)

        Process process = builder.start()

        InputStream stdout = process.getInputStream()

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout))

        String line
        while((line = reader.readLine()) != null){
            log.debug(line)
        }

        process.exitValue()
    }

    /**
     * Runs a bash command and returns its output
     *
     * @param command
     * @return script output as string
     */
    static String runOutput(String command)
    {
        ProcessBuilder builder = new ProcessBuilder(command.split(' '))
        builder.redirectErrorStream(true)

        Process process = builder.start()


        InputStream stdout = process.getInputStream()

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout))

        def line
        StringBuilder sb = new StringBuilder()
        while((line = reader.readLine()) != null){
            sb.append(line)
            sb.append("\n")
        }

        return sb.toString()

    }

    static CmdResponse getResponse(String command){
        ProcessBuilder builder = new ProcessBuilder(command.split(' '))
        builder.redirectErrorStream(true)

        Process process = builder.start()


        InputStream stdout = process.getInputStream()

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout))

        def line
        StringBuilder sb = new StringBuilder()
        while((line = reader.readLine()) != null){
            sb.append(line)
            sb.append("\n")
        }

        CmdResponse response = new CmdResponse()
        response.setExitCode(process.exitValue())
        response.setOutput(sb.toString())

        return response
    }

    static class CmdResponse{
        int exitCode
        String output
    }

}
