package com.yechy.gradleplugin.adb

import org.gradle.internal.os.OperatingSystem

/**
 * @author yechy
 * @date 2018/7/17
 * @email yechengyun@ahgxtx.com
 * @describe 执行shell命令
 *
 */

class ShellUtil {
    public static final String COMMAND_SU       = "su";
    public static final String COMMAND_SH       = "sh";
    public static final String COMMAND_EXIT     = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    private static final String TAG = "ShellUtil";

    private ShellUtil() {
        throw new AssertionError();
    }


    static CommandResult execCommand(String command) {
        int result = -1
        if (command == null) {
            return new CommandResult(result, null, null)
        }

        if (isWindows()) {
            command = "cmd /c " + command
        }

        Process process = null
        StringBuilder successMsg = null
        StringBuilder errorMsg = null

        DataOutputStream os = null
        try {
            GLog.d("Exec command: ${command}")
            process = Runtime.getRuntime().exec(command)
            printShellMessageInNewThread(process.getInputStream(), "inputStream")
            printShellMessageInNewThread(process.getErrorStream(), "errorStream")
            GLog.d("waitFor")
            result = process.waitFor()
            GLog.d("waitFor return " + result)

        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            if (process != null) {
                process.destroy()
            }
        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
                : errorMsg.toString())
    }

    static boolean isWindows() {
        OperatingSystem.current().isWindows()
    }

    /**
     * result of command
     * <ul>
     * <li>{@link CommandResult#result} means result of command, 0 means normal, else means error, same to excute in
     * linux shell</li>
     * <li>{@link CommandResult#successMsg} means success message of command result</li>
     * <li>{@link CommandResult#errorMsg} means error message of command result</li>
     * </ul>
     *
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
     */
    static class CommandResult {

        /** result of command **/
        public int    result
        /** success message of command result **/
        public String successMsg
        /** error message of command result **/
        public String errorMsg

        public CommandResult(int result) {
            this.result = result
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result
            this.successMsg = successMsg
            this.errorMsg = errorMsg
        }

        @Override
        public String toString() {
            return "CommandResult{" +
                    "result=" + result +
                    ", successMsg='" + successMsg + '\'' +
                    ", errorMsg='" + errorMsg + '\'' +
                    '}';
        }
    }

    private static void printShellMessageInNewThread(final InputStream inputStream, final String streamType) {
        GLog.d("printShellMessageInNewThread() " + streamType)
        new Thread(new Runnable() {
            @Override
            public void run() {
                Reader reader = new InputStreamReader(inputStream)
                BufferedReader bufferedReader = new BufferedReader(reader)
                String s
                try {
                    while ((s = bufferedReader.readLine()) != null) {
                        GLog.d("print " + streamType + " shell message: " + s)
                    }
                } catch (IOException e) {
                    e.printStackTrace()
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace()
                    }
                }
                GLog.d("exit print " + streamType + " shell message thread()")
            }
        }).start()
    }
}
