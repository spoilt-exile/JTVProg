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

package jtvprog;

import java.util.*;

/**
 * Class of channel set type
 * @author Stanislav Nepochatov
 */
public class chSet {
    
    chSet() {
        
    }
    
    chSet(ArrayList<chEntry> givenChList) {
        this.chList = givenChList;
    }
    
    /**
     * ArrayList of channels
     */
    private ArrayList<chEntry> chList = new ArrayList();
    
    /**
     * Last found channel entry by isPresent method;
     * @see #isPresnt(java.lang.String) isPresent
     */
    private chEntry lastFound;
    
    /**
     * Class for channel entry
     */
    protected class chEntry implements Cloneable {
        
        /**
         * Default constructor
         * @param GivenChName name of the channel;
         * @param GivenChFOrder number of channel in fill order;
         * @param GivenChROrder number of channel in release order;
         */
        chEntry(String GivenChName, Integer GivenChFOrder, Integer GivenChROrder, String GivenChFilename) {
            this.chName = GivenChName;
            this.chFillOrder = GivenChFOrder;
            this.chReleaseOrder = GivenChROrder;
            this.chFilename = GivenChFilename;
        }
        
        /**
         * Channel name
         */
        public String chName;
        
        /**
         * Channel fill order number
         */
        public Integer chFillOrder;
        
        /**
         * Channel release order number
         */
        public Integer chReleaseOrder;
        
        /**
         * Channel tempfile name
         */
        public String chFilename;
        
        /**
         * Get copy of chEntry
         * @return copy of object
         */
        public chEntry getCopy() {
            chEntry returned = null;
            try {
                returned = (chEntry) this.clone();
            } catch (CloneNotSupportedException ex) {
                JTVProg.logPrint(this, 1, "ошибка возвращения копии канала!");
            }
            return returned;
        }
    }
    
    /**
     * Add channel to set
     * @param chName name of the channel;
     * @param chFOrder number of channel in fill order;
     * @param chROrder number of channel in release order;
     */
    public void addChannel(String chName, Integer chFOrder, Integer chROrder, String chFileName) {
        chEntry channel = new chEntry(chName, chFOrder, chROrder, chFileName);
        if (chList.contains(channel) == false) {
            chList.add(channel);
        } else {
            JTVProg.logPrint(this, 2, "ошибка добавления: канал уже присутствует в списке [" + chName + "]");
        }
    }
    
    /**
     * Find is channel entry is already in chList
     * @param chName name of the channel;
     * @return true if channel is present/false if absent;
     */
    public Boolean isPresnt(String chName) {
        Boolean res = false;
        for (Integer chCounter = 0; chCounter < chList.size(); chCounter++) {
            chEntry channel = chList.get(chCounter);
            if (channel.chName.equals(chName)) {
                lastFound = channel;
                res = true;
                break;
            }
        }
        if (res == false) {
            JTVProg.logPrint(this, 2, "канал с указаннм именем не существует (" + chName + ")");
        }
        return res;
    }
    
    /**
     * Remove channel from chList
     * @param chName name of the channel which will be deleted
     */
    public void removeChannel(String chName) {
        if (this.isPresnt(chName) == true) {
            JTVProg.logPrint(this, 2, "удаление канала [" + chName + "]");
            Integer remFOrder = this.lastFound.chFillOrder;
            Integer remROrder = this.lastFound.chReleaseOrder;
            chList.remove(lastFound);
            for (Integer chCounter = 0; chCounter < chList.size(); chCounter++) {
                chEntry channel = chList.get(chCounter);
                if (channel.chFillOrder > remFOrder) {
                    channel.chFillOrder--;
                }
                if (channel.chReleaseOrder > remROrder) {
                    channel.chReleaseOrder--;
                }
            }
        }
    }
    
    /**
     * Get channels in fill order;
     * @return array with channel names by fill order
     */
    public String[] getChannelsByFillOrder() {
        String[] res = new String[chList.size()];
        for (Integer chCounter = 0; chCounter < res.length; chCounter++) {
            chEntry channel = chList.get(chCounter);
            res[channel.chFillOrder - 1] = channel.chName;
        }
        return res;
    }
    
    /**
     * Get channels in fill order;
     * @return array with channel names by release order
     */
    public String[] getChannelsByReleaseOrder() {
        String[] res = new String[chList.size()];
        for (Integer chCounter = 0; chCounter < res.length; chCounter++) {
            chEntry channel = chList.get(chCounter);
            res[channel.chReleaseOrder - 1] = channel.chName;
        }
        return res;
    }
    
    /**
     * Get size of set
     * @return size of channel set
     */
    public Integer getSetSize() {
        return chList.size();
    }
    
    /**
     * Get channel name by fill order
     * @param chFOrder id of fill order
     * @return channel name
     */
    public String getChannelByFOrder(Integer chFOrder) {
        String res = "";
        for (Integer chCounter = 0; chCounter < chList.size(); chCounter++) {
            chEntry channel = chList.get(chCounter);
            if (channel.chFillOrder == chFOrder) {
                res = channel.chName;
                this.lastFound = channel;
                break;
            }
        }
        return res;
    }
        
    /**
     * Get channel name by release order
     * @param chROrder id of release order
     * @return channel name
     */
    public String getChannelByROrder(Integer chROrder) {
        String res = "";
        for (Integer chCounter = 0; chCounter < chList.size(); chCounter++) {
            chEntry channel = this.chList.get(chCounter);
            if (channel.chReleaseOrder == chROrder) {
                res = channel.chName;
                this.lastFound = channel;
                break;
            }
        }
        return res;
    }
    
    /**
     * Get file name of channel by fill order index
     * @param chFOrder index in fill order
     * @return filename of channel
     */
    public String getFilenameByFOrder (Integer chFOrder) {
        String res = "";
        for (Integer chCounter = 0; chCounter < chList.size(); chCounter++) {
            chEntry channel = this.chList.get(chCounter);
            if (channel.chFillOrder == chFOrder) {
                res = channel.chFilename;
                this.lastFound = channel;
                break;
            }
        }
        return res;
    }
    
    /**
     * Move channel up in fill order
     * @param givenIndex index of selected channel
     */
    public void moveChannelFillUp(Integer givenIndex) {
        String givenChannelName = this.getChannelByFOrder(givenIndex + 1);
        chEntry givenChannel = this.lastFound;
        String placedChannelName = this.getChannelByFOrder(givenIndex);
        chEntry placedChannel = this.lastFound;
        placedChannel.chFillOrder++;
        givenChannel.chFillOrder--;
        JTVProg.logPrint(this, 3, "новый индекс =" + givenChannel.chFillOrder);
    }
    
    /*
     * Move channel down in fill order
     * @param givenIndex index of selected channel
     */
    public void moveChannelFillDown(Integer givenIndex) {
        String givenChannelName = this.getChannelByFOrder(givenIndex + 1);
        chEntry givenChannel = this.lastFound;
        String placedChannelName = this.getChannelByFOrder(givenIndex + 2);
        chEntry placedChannel = this.lastFound;
        placedChannel.chFillOrder--;
        givenChannel.chFillOrder++;
    }
    
     /**
     * Move channel up in release order
     * @param givenIndex index of selected channel
     */
    public void moveChannelReleaseUp(Integer givenIndex) {
        String givenChannelName = this.getChannelByROrder(givenIndex + 1);
        chEntry givenChannel = this.lastFound;
        String placedChannelName = this.getChannelByROrder(givenIndex);
        chEntry placedChannel = this.lastFound;
        placedChannel.chReleaseOrder++;
        givenChannel.chReleaseOrder--;
    }
    
    /*
     * Move channel down in release order
     * @param givenIndex index of selected channel
     */
    public void moveChannelReleaseDown(Integer givenIndex) {
        String givenChannelName = this.getChannelByROrder(givenIndex + 1);
        chEntry givenChannel = this.lastFound;
        String placedChannelName = this.getChannelByROrder(givenIndex + 2);
        chEntry placedChannel = this.lastFound;
        placedChannel.chReleaseOrder--;
        givenChannel.chReleaseOrder++;
    }
    
    /**
     * Pull chList from object;
     * @return list of channels and orders;
     */
    public ArrayList pullList() {
        ArrayList<chEntry> returned = new ArrayList();
        ListIterator chIterator = this.chList.listIterator();
        while (chIterator.hasNext()) {
            chEntry Channel = (chEntry) chIterator.next();
            returned.add(Channel.getCopy());
        }
        JTVProg.logPrint(this, 2, "возвращение списка каналов");
        return returned;
    }
    
    /**
     * Push specified channel list in object;
     * @param givenChList list of channels and orders;
     */
    public void pushList(ArrayList givenChList) {
        JTVProg.logPrint(this, 2, "вставка списка каналов");
        this.chList = givenChList;
    }
    
    /**
     * Set major channel stats
     * @param chFOrder index in fill order
     * @param newName new name of channel
     * @param newFile new filename of channel file
     */
    public void setChannelStats(Integer chFOrder, String newName, String newFile) {
        String channelName = this.getChannelByFOrder(chFOrder);
        JTVProg.logPrint(this, 2, "изменение данных канала: [" + this.lastFound.chName + "->" + newName + "]");
        this.lastFound.chName = newName;
        this.lastFound.chFilename = newFile;
    }
    
    @Override
    public String toString() {
        String res = "";
        for (Integer chCounter = 0; chCounter < chList.size(); chCounter++) {
            chEntry channel = chList.get(chCounter);
            res = res.concat("'" + channel.chName + "':F=" + channel.chFillOrder + ";R=" + channel.chReleaseOrder + ":File=" + channel.chFilename + "\n");
        }
        return res;
    }
    
    /**
     * Store all channel in specified properties
     * @param inputProperties 
     */
    public final void storeToProperties(java.util.Properties inputProperties) {
        inputProperties.setProperty("tv_set.last_id", String.valueOf(this.getSetSize()));
        for (Integer id = 1; id <= this.getSetSize(); id++) {
            String tvPattern = "tv_set.channel_" + id;
            String chName = this.getChannelByFOrder(id);
            chEntry Channel =  this.lastFound;
            try {
                inputProperties.setProperty(tvPattern + ".name", new String(chName.getBytes(), JTVProg.configer.sysEncoding));
                inputProperties.setProperty(tvPattern + ".file_name", new String(Channel.chFilename.getBytes(), JTVProg.configer.sysEncoding));
            } catch (java.io.UnsupportedEncodingException ex) {
                JTVProg.logPrint(this, 1, "ошибка кодирования файла конфигурации");
            }
            inputProperties.setProperty(tvPattern + ".fill_order", String.valueOf(Channel.chFillOrder));
            inputProperties.setProperty(tvPattern + ".release_order", String.valueOf(Channel.chReleaseOrder));
        }
    }
}
