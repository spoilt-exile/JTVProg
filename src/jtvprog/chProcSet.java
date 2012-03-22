/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Channel processing set class
 * @author Stanislav Nepochatov
 */
public class chProcSet extends chSet{
    
    /**
     * System dependent line seporator
     */
    private static String lineSeparator = System.getProperty("line.separator");
    
    /**
     * Relative path for channel release
     */
    private String CH_PATH = "по каналам/";
    
    /**
     * Relative path for day release
     */
    private String DAY_PATH = "по дням/";
    
    /**
     * Relative path for ASOP release
     */
    //private String ASOP_PATH = "ASOP/";
    
    /**
     * List of channels processors units
     */
    private java.util.ArrayList<chProcUnit> chProcList = new java.util.ArrayList();
    
    /**
     * Current processor unit
     */
    protected chProcUnit currentUnit;
    
    /**
     * Current inputed channel in stack;
     */
    public Integer currentIndex = 1;
    
    /**
     * Current channel name
     */
    public String currentChName = "";
    
    /**
     * Enumeration of states for channel processor;
     */
    private enum states {
        /**
         * Processor doing nothing
         */
        EMPTY,
        
        /**
         * Processor now at input state
         */
        INPUT,
        
        /**
         * Processor now at output state
         */
        OUTPUT};
    
    /**
     * Default state of processor
     */
    private states currentState = states.EMPTY;
    
    /**
     * Files for day release
     */
    private java.io.File[] outDays = new java.io.File[7];
    
    /**
     * 
     */
    private String[] daysHeaders = new String[7]; 
    
    /**
     * Array of string constants for days of week
     */
    private String[] daysPatterns = new String[] {"Понедельник,", "Вторник,", "Среда,", "Четверг,", "Пятница,", "Суббота,", "Воскресенье,"};
    
    /**
     * Output stack for program relese<br>
     * <b>[0]</b> - headers of channels;<br>
     * <b>[1]</b> - channels content;<br>
     * <b>[2]</b> - headers of days;<br>
     * <b>[3]</b> - day's content;
     */
    private java.util.ArrayList<String>[] outputStack = new java.util.ArrayList[4];
    
    /**
     * Operatioanl output index flag
     */
    private Integer operFlag = 0;
    
    /**
     * Operational output stack for headers
     */
    public java.util.ArrayList<String> operOutHeaders;
    
    /**
     * Operational output stack for current release mode
     */
    public java.util.ArrayList<String> operOutStack;
    
    /**
     * Maximum length of message.
     * Changed to 13000 in order to fix issue with applying messages
     */
    private static int maxLength = 13000;
    
    /**
     * Empty constructor
     */
    chProcSet() {
        new java.io.File(CH_PATH).mkdir();
        new java.io.File(DAY_PATH).mkdir();
       // new java.io.File(ASOP_PATH).mkdir();
    }
    
    /**
     * Construct set with given list of channels
     * @param givenChList list of channels from chSet object
     */
    chProcSet(java.util.ArrayList<chEntry> givenChList) {
        this();
        java.util.ListIterator<chEntry> chIter = givenChList.listIterator();
        while (chIter.hasNext()) {
            chProcUnit tempProcUnit = new chProcUnit(chIter.next());
            this.chProcList.add(tempProcUnit);
        }
    }
    
    /**
     * Channel processing unit
     */
    protected class chProcUnit extends chEntry {
        
        /**
         * Empty constructor
         */
        chProcUnit() {
            super ("", 0, 0, "");
        }
        
        /**
         * Counstruct from arguments
         * @param GivenChName
         * @param GivenChFOrder
         * @param GivenChROrder
         * @param GivenChFilename 
         */
        chProcUnit(String GivenChName, Integer GivenChFOrder, Integer GivenChROrder, String GivenChFilename) {
            super(GivenChName, GivenChFOrder, GivenChROrder, GivenChFilename);
        }
        
        /**
         * Construct from chEntry
         * @param givenChannel 
         */
        chProcUnit(chEntry givenChannel) {
            super(givenChannel.chName, givenChannel.chFillOrder, givenChannel.chReleaseOrder, givenChannel.chFilename);
        }
        
        /**
         * File object for this unit
         */
        private java.io.File chFile = new java.io.File(CH_PATH + this.chFilename);
        
        /**
         * Temporary string content of channel
         */
        public String chStored = "";
        
        /**
         * Write string to file and close it.
         */
        public void writeChannel() {
            try {
                java.io.FileWriter chWriter = new java.io.FileWriter(chFile);
                chWriter.write(chStored);
                chWriter.close();
                JTVProg.logPrint(this, 3, "файл канала [" + this.chName + "] успешно сохранен");
            } catch (IOException ex) {
                JTVProg.logPrint(this, 0, "ошибка записи файла канала [" + this.chName + "]");
            }
        }
        
        /**
         * Read channel content from file
         */
        public void readChannel() {
            this.chStored = "";
            try {
                java.io.FileReader chReader = new java.io.FileReader(chFile);
                while (chReader.ready()) {
                    this.chStored = this.chStored + chReader.read();
                }
                chReader.close();
            } catch (FileNotFoundException ex) {
                JTVProg.logPrint(this, 0, "файл канала [" + this.chName + "] не найден");
            } catch (IOException ex) {
                JTVProg.logPrint(this, 0, "ошибка чтения файла канала [" + this.chName + "]");
            }
        }
        
        @Override
        public String toString() {
            return "[" + this.chName + " ," + this.chFillOrder + " ," + this.chReleaseOrder + " ," + this.chFilename + "]";
        }
    }
    
    /**
     * Get processor unit according to state of processors set
     * @param index index of unit
     * @return chProcUnit object
     */
    private chProcUnit getUnit(Integer index) {
        chProcUnit returnedUnit = new chProcUnit();
        if (currentState == states.INPUT) {
            java.util.ListIterator<chProcUnit> chIter = chProcList.listIterator();
            while (chIter.hasNext()) {
                chProcUnit tempUnit = chIter.next();
                if (tempUnit.chFillOrder == index) {
                    returnedUnit = tempUnit;
                    break;
                }
            }
        } else if (currentState == states.OUTPUT) {
            java.util.ListIterator<chProcUnit> chIter = chProcList.listIterator();
            while (chIter.hasNext()) {
                chProcUnit tempUnit = chIter.next();
                if (tempUnit.chReleaseOrder == index) {
                    returnedUnit = tempUnit;
                    break;
                }
            }
        } else {
            JTVProg.logPrint(this, 1, "ошибка вызова: процессор без состояния");
        }
        return returnedUnit;
    }
    
    /**
     * Get file content as string
     * @param fileObj file descriptor object
     * @return string content of given file object
     */
    private String getFileContent(java.io.File fileObj) {
        String returned = "";
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(fileObj));
            while (reader.ready()) {
                returned = returned + reader.readLine() + lineSeparator;
            }
        } catch (FileNotFoundException ex) {
            JTVProg.logPrint(this, 0, "файл [" + fileObj.getName() + "] не найден");
        } catch (IOException ex) {
            JTVProg.logPrint(this, 0, "[" + fileObj.getName() + "]: ошибка ввода/вывода");
        }
        return returned;
    }
    
    /**
     * Prepeare set for channels input
     */
    public void beginInput() {
        this.currentState = states.INPUT;
        this.currentIndex = 1;
        this.currentUnit = getUnit(this.currentIndex);
        this.currentChName = this.currentUnit.chName;
    }
    
    /**
     * End input and unlock processing mode
     */
    public void endInput() {
        this.currentIndex = 1;
        this.currentUnit = null;
        this.currentChName = null;
        this.currentState = states.EMPTY;
        JTVProg.mainWindow.tvFillBut.setEnabled(false);
        JTVProg.mainWindow.tvProcBut.setEnabled(true);
    }
    
    /**
     * Move processor stack to input next channel
     */
    public void inputNext() {
        ++this.currentIndex;
        this.currentUnit = getUnit(this.currentIndex);
        this.currentChName = this.currentUnit.chName;
    }
    
    /**
     * Move processor stack to input previous channel
     */
    public void inputPrev() {
        --this.currentIndex;
        this.currentUnit = getUnit(this.currentIndex);
        this.currentChName = this.currentUnit.chName;
    }
    
    /**
     * Get current channel content
     * @return content current content of channel
     */
    public String getCurrentContent() {
        return this.currentUnit.chStored;
    }
    
    /**
     * Get size of set
     * @return size of channel processor set
     */
    @Override
    public Integer getSetSize() {
        return this.chProcList.size();
    }
    
    /**
     * Check channel input 
     * @param content current content of channel
     * @return true if given content correspond to current channel / false of not
     */
    public Boolean checkInput(String content) {
        Boolean returned = false;
        if (content.contains(this.currentChName)) {
            returned = true;
        }
        returned = returned & this.preProcess(content);
        return returned;
    }
    
    /**
     * General check of channel format
     * @param content current content of channel
     * @return true if channel content contains all days of week
     */
    private Boolean preProcess(String content) {
        Boolean returned = true;
        for (Integer dayIndex = 0; dayIndex < this.daysPatterns.length; dayIndex++) {
            if (content.contains(this.daysPatterns[dayIndex]) == false) {
                JTVProg.logPrint(this, 1, "[" + this.currentChName + "]: текст не полон");
                returned = false;
                break;
            }
        }
        return returned;
    }
    
    /**
     * Perform channel input
     * @param content content current content of channel
     */
    public void performInput(String content) {
        String gmtLabel = "GMT + 2";
        if (content.contains(gmtLabel)) {
            content = content.substring(content.indexOf(gmtLabel) + gmtLabel.length()).trim();
        }
        this.currentUnit.chStored = content;
        this.currentUnit.writeChannel();
    }
    
    /**
     * Process channel for day release (init method)
     */
    public void processDays() {
        JTVProg.logPrint(this, 3, "начата обработка каналов");
        String[][] dayMatrix = new String[7][this.getSetSize()];
        java.util.ListIterator<chProcUnit> chUnits = this.chProcList.listIterator();
        while (chUnits.hasNext()) {
            chProcUnit currentProc = chUnits.next();
            JTVProg.logPrint(this, 3, "обработка канала [" + currentProc.chName + "]");
            String[] blocks = currentProc.chStored.split(lineSeparator + lineSeparator);
            for (Integer currBlockIndex = 0; currBlockIndex < blocks.length; currBlockIndex++) {
                String currentBlock = blocks[currBlockIndex];
                String blockHeader = pickHead(currentBlock);
                Integer currentDayIndex;
                if ((currentDayIndex = recognizeDay(blockHeader)) != -1) {
                    JTVProg.logPrint(this, 3, "добавление дня [" + blockHeader + "]");
                    if (daysHeaders[currentDayIndex] == null) {
                        daysHeaders[currentDayIndex] = blockHeader;
                    }
                    dayMatrix[currentDayIndex][currentProc.chReleaseOrder - 1] = currentProc.chName + currentBlock.substring(blockHeader.length());
                } else {
                    JTVProg.logPrint(this, 2, "блок [" + currBlockIndex + "] не является днем");
                }
            }
        }
        for (Integer currFileIndex = 0; currFileIndex < this.outDays.length; currFileIndex++) {
            this.outDays[currFileIndex] = new java.io.File(this.DAY_PATH + this.daysHeaders[currFileIndex] + ".txt");
            String dayContent = this.daysHeaders[currFileIndex];
            for (Integer currChannelIndex = 0; currChannelIndex < this.getSetSize(); currChannelIndex++) {
                String channelBlock = dayMatrix[currFileIndex][currChannelIndex];
                if (channelBlock == null) {
                    JTVProg.logPrint(this, 1, "блок канала пуст! [" + currFileIndex + "," + currChannelIndex + "]");
                    channelBlock = "ПУСТОЙ БЛОК!!! [" + currFileIndex + "," + currChannelIndex + "]";
                }
                dayContent = dayContent + lineSeparator + lineSeparator + channelBlock;
            }
            try {
                java.io.FileWriter dayWriter = new java.io.FileWriter(this.outDays[currFileIndex]);
                dayWriter.write(dayContent);
                dayWriter.close();
                JTVProg.logPrint(this, 3, "файл дня [" + this.daysHeaders[currFileIndex] + "] успешно сохранен");
            } catch (IOException ex) {
                JTVProg.logPrint(this, 0, "ошибка записи файла дня [" + this.daysHeaders[currFileIndex] + "]");
            }
        }
    }
    
    /**
     * Pick and return first line in string block (pick date header)
     * @param block string of block in channel stack
     * @return first line of given string block
     * @see #processDays() 
     */
    private String pickHead(String block) {
        return block.split("\n")[0].trim();
    }
    
    /**
     * Recognize block header as day of weak
     * @param header first line of string block
     * @return index of day in array
     * @see #processDays()
     */
    private Integer recognizeDay(String header) {
        Integer returned = -1;
        for (Integer day = 0; day < 7; day++) {
            if (header.contains(this.daysPatterns[day])) {
                returned = day;
                break;
            }
        }
        return returned;
    }
    
    /**
     * Preapeare set for output mode
     */
    public void beginOutput() {
        this.currentState = states.OUTPUT;
        this.buildOutStack();
        this.setOutputMode(0);
    }
    
    /**
     * Build output stack with text splitting
     */
    private void buildOutStack() {
        this.outputStack[0] = new java.util.ArrayList<String>();
        this.outputStack[1] = new java.util.ArrayList<String>();
        this.outputStack[2] = new java.util.ArrayList<String>();
        this.outputStack[3] = new java.util.ArrayList<String>();
        JTVProg.logPrint(this, 3, "подготовка выпуска по каналам");
        for (Integer currentRIndex = 1; currentRIndex < this.getSetSize() + 1; currentRIndex++) {
            chProcUnit currentProc = this.getUnit(currentRIndex);
            if (currentProc.chStored.length() > maxLength) {
                java.util.ArrayList<String> splittedStored = this.textSplit(currentProc.chStored);
                String chHeader = this.pickHead(currentProc.chStored);
                java.util.ListIterator<String> splitIter = splittedStored.listIterator();
                Integer currentSplitIndex = 1;
                while (splitIter.hasNext()) {
                    String currentSplitText = splitIter.next();
                    this.outputStack[0].add("БЛ-" + currentSplitIndex + ":" + chHeader);
                    this.outputStack[1].add(currentSplitText.replace(chHeader, "БЛ-" + currentSplitIndex + ":" + chHeader));
                    ++currentSplitIndex;
                }
            }
            else {
                this.outputStack[0].add(currentProc.chName);
                this.outputStack[1].add(currentProc.chStored);
            }
        }
        JTVProg.logPrint(this, 3, "подготовка выпуска по дням");
        for (Integer dayFileIndex = 0; dayFileIndex < outDays.length; dayFileIndex++) {
            java.io.File currDayFile = this.outDays[dayFileIndex];
            String dayContent = this.getFileContent(currDayFile);
            if (dayContent.length() > maxLength) {
                java.util.ArrayList<String> splittedDay = this.textSplit(dayContent);
                String dayHeader = this.pickHead(dayContent);
                java.util.ListIterator<String> splitIter = splittedDay.listIterator();
                Integer currentSplitIndex = 1;
                while (splitIter.hasNext()) {
                    String currentSplitText = splitIter.next();
                    this.outputStack[2].add("БЛ-" + currentSplitIndex + ":" + dayHeader);
                    this.outputStack[3].add(currentSplitText.replace(dayHeader, "БЛ-" + currentSplitIndex + ":" + dayHeader));
                    ++currentSplitIndex;
                }
            }
            else {
                this.outputStack[2].add(this.pickHead(dayContent));
                this.outputStack[3].add(dayContent);
            }
        }
    }
    
    /**
     * Split text to proper release in ASOP system
     * @param splitted given text
     * @return ArrayList with splitted text
     */
    private java.util.ArrayList<String> textSplit(String splitted) {
        java.util.ArrayList<String> returned = new java.util.ArrayList<String>();
        String[] pieces = splitted.split(lineSeparator + lineSeparator);
        String rstr = "";
        for (int cpiece = 0; cpiece < pieces.length; cpiece++) {
            String cstr = pieces[cpiece];
            if (rstr.length() + cstr.length() > maxLength) {
                if (!rstr.isEmpty()) {
                    returned.add(rstr);
                    rstr = cstr;
                }
                else {
                    returned.add(cstr);
                }
            }
            else {
                if (rstr.equals("")) {
                    rstr = cstr;
                }
                else {
                    rstr = rstr + lineSeparator + lineSeparator + cstr;
                }
            }
            if (cpiece == pieces.length - 1) {
                returned.add(rstr);
            }
        }
        return returned;
    }
    
    /**
     * Set mode for output (channel/day)
     * @param givenFlag number of flag state (0 - channel output/1 - day output);
     */
    public void setOutputMode(Integer givenFlag) {
        switch (givenFlag) {
            case 0:
                this.operFlag = givenFlag;
                this.operOutHeaders = this.outputStack[0];
                this.operOutStack = this.outputStack[1];
                break;
            case 1:
                this.operFlag = givenFlag;
                this.operOutHeaders = this.outputStack[2];
                this.operOutStack = this.outputStack[3];
                break;
        }
        this.currentIndex = 1;
        this.currentChName = this.operOutHeaders.get(this.currentIndex - 1);
    }
    
    /**
     * Move processor stack to output next item
     */
    public void outputNext() {
        ++this.currentIndex;
        this.currentChName = this.operOutHeaders.get(this.currentIndex - 1);
    }
    
    /**
     * Move processor stack to output previous item
     */
    public void outputPrev() {
        --this.currentIndex;
        this.currentChName = this.operOutHeaders.get(this.currentIndex - 1);
    }
}
