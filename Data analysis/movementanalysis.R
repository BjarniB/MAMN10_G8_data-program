
library("zoo")
library("matrixStats")
library("plyr")
setwd("~/Documents/Master CogSci/Neuro-modelling, cognitive robotics & agents /Project - robot animation/MAMN10_G8_data-program/Data analysis")
'''
Bjarni_peka_armbage = read.delim("DataCleanup/humpfiles/Bjarni_peka_armbage_raw.csv", header=FALSE)
Bjarni_peka_axel = read.delim("DataCleanup/humpfiles/Bjarni_peka_axel_raw.csv", header=FALSE)
#Bjarni_peka_hand_ = read.delim("DataCleanup/humpfiles/Bjarni_peka_hand_hump.csv", header=FALSE)
Bjarni_peka_hand = read.delim("DataCleanup/humpfiles/Bjarni_peka_hand_raw.csv", header=FALSE)
Bjarni_pull21_armbage = read.delim("DataCleanup/humpfiles/Bjarni_pull21_armbage_raw.csv", header=FALSE)
Bjarni_pull21_axel = read.delim("DataCleanup/humpfiles/Bjarni_pull21_axel_raw.csv", header=FALSE)
Bjarni_pull21_hand = read.delim("DataCleanup/humpfiles/Bjarni_pull21_hand_raw.csv", header=FALSE)
Bjarni_pull31_armbage = read.delim("DataCleanup/humpfiles/Bjarni_pull31_armbage_raw.csv", header=FALSE)
Bjarni_pull31_axel = read.delim("DataCleanup/humpfiles/Bjarni_pull31_axel_raw.csv", header=FALSE)
Bjarni_pull31_hand = read.delim("DataCleanup/humpfiles/Bjarni_pull31_hand_raw.csv", header=FALSE)
Bjarni_pull32_armbage = read.delim("DataCleanup/humpfiles/Bjarni_pull32_armbage_raw.csv", header=FALSE)
Bjarni_pull32_axel = read.delim("DataCleanup/humpfiles/Bjarni_pull32_axel_raw.csv", header=FALSE)
Bjarni_pull32_hand = read.delim("DataCleanup/humpfiles/Bjarni_pull32_hand_raw.csv", header=FALSE)
Bjarni_push12_armbage = read.delim("DataCleanup/humpfiles/Bjarni_push12_armbage_raw.csv", header=FALSE)
Bjarni_push12_axel = read.delim("DataCleanup/humpfiles/Bjarni_push12_axel_raw.csv", header=FALSE)
Bjarni_push12_hand = read.delim("DataCleanup/humpfiles/Bjarni_push12_hand_raw.csv", header=FALSE)
Bjarni_push13_armbage = read.delim("DataCleanup/humpfiles/Bjarni_push13_armbage_raw.csv", header=FALSE)
Bjarni_push13_axel = read.delim("DataCleanup/humpfiles/Bjarni_push13_axel_raw.csv", header=FALSE)
Bjarni_push13_hand = read.delim("DataCleanup/humpfiles/Bjarni_push13_hand_raw.csv", header=FALSE)
Bjarni_push23_armbage = read.delim("DataCleanup/humpfiles/Bjarni_push23_armbage_raw.csv", header=FALSE)
Bjarni_push23_axel = read.delim("DataCleanup/humpfiles/Bjarni_push23_axel_raw.csv", header=FALSE)
Bjarni_push23_hand = read.delim("DataCleanup/humpfiles/Bjarni_push23_hand_raw.csv", header=FALSE)
Bjarni_shove1_armbage = read.delim("DataCleanup/humpfiles/Bjarni_shove1_armbage_raw.csv", header=FALSE)
Bjarni_shove1_axel = read.delim("DataCleanup/humpfiles/Bjarni_shove1_axel_raw.csv", header=FALSE)
Bjarni_shove1_hand = read.delim("DataCleanup/humpfiles/Bjarni_shove1_hand_raw.csv", header=FALSE)
Bjarni_shove2_armbage = read.delim("DataCleanup/humpfiles/Bjarni_shove2_armbage_raw.csv", header=FALSE)
Bjarni_shove2_axel = read.delim("DataCleanup/humpfiles/Bjarni_shove2_axel_raw.csv", header=FALSE)
Bjarni_shove2_hand = read.delim("DataCleanup/humpfiles/Bjarni_shove2_hand_raw.csv", header=FALSE)
Bjarni_shove3_armbage = read.delim("DataCleanup/humpfiles/Bjarni_shove3_armbage_raw.csv", header=FALSE)
Bjarni_shove3_axel = read.delim("DataCleanup/humpfiles/Bjarni_shove3_axel_raw.csv", header=FALSE)
Bjarni_shove3_hand = read.delim("DataCleanup/humpfiles/Bjarni_shove3_hand_raw.csv", header=FALSE)
Bjarni_sweep1_axel_raw_use = read.delim("DataCleanup/humpfiles/Bjarni_sweep1_axel_raw_useless.csv", header=FALSE)
Bjarni_sweep1_hand = read.delim("DataCleanup/humpfiles/Bjarni_sweep1_hand_raw.csv", header=FALSE)

# script for statistics across movement files
timeseries = ts(Bjarni_peka_hand, frequency=25)
# mean across cols: colMean()
avg = rowMeans(Bjarni_peka_hand[2:,])
# sd across cols: colSds
sd = colSds(timeseries)
plot

library(ggplot2)


do_plot <- function (arg, title=""){
  mat = t(arg)
  Mat1 = mat[,-1] # index from 2 to end (end to beginning and one back)
  d = data.frame(t=mat[,1],m=rowMeans(Mat1), sdd=rowSds(Mat1))
  
  
  plt = ggplot(d, aes(t, m)) + geom_line(stat = "identity")
  plt = plt + geom_ribbon(aes(ymin=m-sdd, ymax=m+sdd), alpha = 0.2)
  plt = plt + ylab("Distance") + xlab("Time")
  plt = plt + ggtitle(title)
  plt
}

do_plot(Bjarni_pull21_hand, title="Bjarni pull 21 hand") # not split
do_plot(Bjarni_pull21_armbage) # not split
do_plot(Bjarni_pull21_axel) # not split
do_plot(Bjarni_pull32_hand) # not split
do_plot(Bjarni_pull32_armbage) # not split
do_plot(Bjarni_pull32_axel) # not split
do_plot(Bjarni_pull31_hand) # not split
do_plot(Bjarni_pull31_armbage) # not split
do_plot(Bjarni_pull31_axel, title="Bjarni pull 31 axel") 

do_plot(Bjarni_push12_hand)
do_plot(Bjarni_push12_armbage)
do_plot(Bjarni_push12_axel) 
do_plot(Bjarni_push13_hand)
do_plot(Bjarni_push13_armbage)
do_plot(Bjarni_push13_axel)
do_plot(Bjarni_push23_hand) # not split
do_plot(Bjarni_push23_armbage) # not split
do_plot(Bjarni_push23_axel) # not split

do_plot(Bjarni_shove1_hand)
do_plot(Bjarni_shove1_armbage)
do_plot(Bjarni_shove1_axel)
do_plot(Bjarni_shove2_hand)
do_plot(Bjarni_shove2_armbage)
do_plot(Bjarni_shove2_axel)
do_plot(Bjarni_shove3_hand) # not split
do_plot(Bjarni_shove3_armbage) # not split
do_plot(Bjarni_shove3_axel) # not split

do_plot(Bjarni_sweep1_hand)
'''

#---

# Sequence:
# make a list with Bjarni, Asas, Trond
person = c("Asas", "Bjarni", "Trond")
filelistname = "dir.txt"
dataset = data.frame()
first = TRUE
time = seq(0, 3, 0.04)
#make a data frame for everything: [name of person][name of file][hump data with time-trials as rows]
for (name in person){
  
  path = paste("DataCleanup/humpfiles/", name, "/", sep="") 
  #read in file with names of csv files, found in humpfiles/Name/
  files = read.delim(paste(path, filelistname, sep=""), header=FALSE)
  #print(files)

  for (csvfile in files[,1]){
    fn = paste(path, csvfile, sep="")
    tmp = read.delim(fn, header=FALSE)
    
    
    time = tmp[1,] #first row
    
      
    tmp = tmp[-1,]
    #get numer of rows of tmp
    numrows = nrow(tmp)
    tmp = data.frame(tmp)
    #fill a vector with (name of file - person_): gsub(paste(person, "_",delim=""), "", csvfile)
    removestr = paste(name, "_", "|_raw.csv", sep="")
    #print(removestr)
    movement = gsub(removestr, "", csvfile)
    #print(movement)
    movementcol = rep(movement, numrows)  
    #fill another vector with name of person: 
    personcol = rep(name, numrows)
    #add vectors as columns to tmp
    #df = data.frame(person=personcol, movement=movementcol, data = tmp)
    df = cbind(personcol, movementcol, tmp)
    #add tmp to bottom of main dataframe
    if(first){
      dataset = df
      first = FALSE
    }
    else{
      # check if number of columns is correct or needs padding
      # too few - add padding on df
      # too many - add padding on dataset
      dataset = rbind.fill(dataset, df)
    }
      
  }
}

#find average and std dev of all movements, across persons
#get unique values of movements: 
movements = dataset$movementcol
movements  = movements[!duplicated(movements)]#, or something
for (mv in movements){
  wavedata = newdata <- subset(mydata, movement=mv, select=c(data1:data50))
}
#wavedata = newdata <- subset(mydata, movement=mv, select=c(data1:data50))
#add time column
#do_plot(wavedata, movement)



plotting:




'''
