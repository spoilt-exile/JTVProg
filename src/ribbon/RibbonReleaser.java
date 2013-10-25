/**
 * This file is part of JTVProg application (check README).
 * Copyright (C) 2013 Stanislav Nepochatov
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
**/

package ribbon;

import jtvprog.JTVProg;

/**
 * Release TV program to Ribbon system.
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
public class RibbonReleaser {
    
    /**
     * Releaser configuration properties.
     */
    private java.util.Properties releaseProps;
    
    /**
     * Channel release flag.
     */
    private Boolean channelRelease;
    
    /**
     * Day release flag.
     */
    private Boolean dayRelease;
    
    /**
     * Allow incomplete relese.
     */
    private Boolean allowIncomplete;
    
    /**
     * Display if current status string is error or just message. 
     * By default equal <code>false</code> (status is message).
     */
    private Boolean errStatus = false;
    
    /**
     * Status of execution.
     */
    private String status;
    
    /**
     * Default constructor.
     */
    public RibbonReleaser() {
        releaseProps = JTVProg.tvApp.getProperties("Release.properties", JTVProg.class.getResourceAsStream("Release.properties"));
        this.channelRelease = Boolean.parseBoolean(releaseProps.getProperty("release_channel"));
        this.dayRelease = Boolean.parseBoolean(releaseProps.getProperty("release_day"));
        this.allowIncomplete = Boolean.parseBoolean(releaseProps.getProperty("release_aloow_incomplete"));
    }
    
    /**
     * Render message for the user.
     * @return formatted message;
     */
    public String renderMessage() {
        return "Текущий пользователь: " + JTVProg.tvApp.CURR_LOGIN + "\n\nПараметры выпуска:\n"
                + (this.channelRelease ? "Выпуск каналов разрешен: " + this.releaseProps.getProperty("release_chn_dir") + "\n" : "Выпуск каналов отключен\n") 
                + (this.dayRelease ? "Выпуск по дням разрешен: " + this.releaseProps.getProperty("release_day_dir") + "\n" : "Выпуск по дням отключен\n")
                + (this.allowIncomplete ? "Неполный выпуск разрешен." : "Неполный выпуск запрещен!");
    }
    
    /**
     * Iterate through channels and days and release them to the system.
     */
    public void release() {
        if (!this.allowIncomplete && JTVProg.isPass()) {
            this.status = "Невозможно выпустить: пропущены каналы!";
            this.errStatus = true;
            return;
        }
        if (this.channelRelease) {
            for (int chIndex = 1; chIndex < JTVProg.configer.ChannelProcessor.getSetSize() + 1; chIndex++) {
                jtvprog.chProcSet.chProcUnit currChannel = JTVProg.configer.ChannelProcessor.getUnit(chIndex);
                if (!currChannel.isPassedNull) {
                    String res = this.release(currChannel.chName, currChannel.chStored, this.releaseProps.getProperty("release_chn_dir"));
                    if (res.contains("RIBBON_ERROR:")) {
                        JTVProg.warningMessage("Ошибка выпуска канала " + currChannel.chName + "\n\nОтвет системы:\n"
                                + Generic.CsvFormat.parseDoubleStruct(res)[1]);
                        this.status = currChannel.chName + ": " + Generic.CsvFormat.parseDoubleStruct(res)[1];
                        this.errStatus = true;
                        return;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {}
            }
        }
        if (this.dayRelease) {
            for (int dayIndex = 0; dayIndex < 7; dayIndex++) {
                String res = this.release(JTVProg.configer.ChannelProcessor.daysHeaders[dayIndex], 
                        JTVProg.configer.ChannelProcessor.getFileContent(JTVProg.configer.ChannelProcessor.outDays[dayIndex]), 
                        this.releaseProps.getProperty("release_day_dir"));
                if (res.contains("RIBBON_ERROR:")) {
                    JTVProg.warningMessage("Ошибка выпуска дня " + JTVProg.configer.ChannelProcessor.daysHeaders[dayIndex] + "\n\nОтвет системы:\n"
                            + Generic.CsvFormat.parseDoubleStruct(res)[1]);
                    this.status = JTVProg.configer.ChannelProcessor.daysHeaders[dayIndex] + ": " + Generic.CsvFormat.parseDoubleStruct(res)[1];
                    this.errStatus = true;
                    return;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {}
            }
        }
        this.status = "Выпуск текста телепрограммы удачно завершен.";
    }
    
    /**
     * Release message to the system (universal).
     * @param header header of the message;
     * @param content content of message;
     * @param dirs dirs to apply;
     */
    private String release(String header, String content, String dirs) {
        MessageClasses.Message currMessage = new MessageClasses.Message(header, 
                JTVProg.tvApp.CURR_LOGIN, 
                "RU", 
                dirs.replaceFirst("\\[", "").replaceFirst("\\]", "").split(","),
                new String[] {"телепрограмма", "выпуск"},
                content
                );
        String command;
        command = "RIBBON_POST_MESSAGE:-1," + Generic.CsvFormat.renderGroup(currMessage.DIRS) + "," + "RU" + ",{" +
        currMessage.HEADER + "}," + Generic.CsvFormat.renderGroup(currMessage.TAGS) + "," + 
        Generic.CsvFormat.renderMessageProperties(currMessage.PROPERTIES) + "\n" + currMessage.CONTENT + "\nEND:";
        return JTVProg.tvApp.appWorker.sendCommandWithReturn(command);
    }
    
    /**
     * Return status of releasing.
     * @return status string value;
     */
    public String getStatus() {
        return this.status;
    }
    
    /**
     * Return error flag of this releaing.
     * @return error flag value;
     */
    public Boolean getErrStatus() {
        return this.errStatus;
    }
}
