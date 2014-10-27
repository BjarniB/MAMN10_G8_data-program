
library("zoo")
library("matrixStats")
library("plyr")
setwd("~/Documents/Master CogSci/Neuro-modelling, cognitive robotics & agents /Project - robot animation/MAMN10_G8_data-program/Data analysis")
library(ggplot2)

# Multiple plot function
#
# ggplot objects can be passed in ..., or to plotlist (as a list of ggplot objects)
# - cols:   Number of columns in layout
# - layout: A matrix specifying the layout. If present, 'cols' is ignored.
#
# If the layout is something like matrix(c(1,2,3,3), nrow=2, byrow=TRUE),
# then plot 1 will go in the upper left, 2 will go in the upper right, and
# 3 will go all the way across the bottom.
#
multiplot <- function(..., plotlist=NULL, file, cols=1, layout=NULL) {
  require(grid)
  
  # Make a list from the ... arguments and plotlist
  plots <- c(list(...), plotlist)
  
  numPlots = length(plots)
  
  # If layout is NULL, then use 'cols' to determine layout
  if (is.null(layout)) {
    # Make the panel
    # ncol: Number of columns of plots
    # nrow: Number of rows needed, calculated from # of cols
    layout <- matrix(seq(1, cols * ceiling(numPlots/cols)),
                     ncol = cols, nrow = ceiling(numPlots/cols))
  }
  
  if (numPlots==1) {
    print(plots[[1]])
    
  } else {
    # Set up the page
    grid.newpage()
    pushViewport(viewport(layout = grid.layout(nrow(layout), ncol(layout))))
    
    # Make each plot, in the correct location
    for (i in 1:numPlots) {
      # Get the i,j matrix positions of the regions that contain this subplot
      matchidx <- as.data.frame(which(layout == i, arr.ind = TRUE))
      
      print(plots[[i]], vp = viewport(layout.pos.row = matchidx$row,
                                      layout.pos.col = matchidx$col))
    }
  }
}

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

plot_no_time = function(arg, time, title=""){
  mat = t(arg)
  #Mat1 = mat[,-1] # index from 2 to end (end to beginning and one back)
  d = data.frame(t=time,m=rowMeans(mat), sdd=rowSds(mat))
  
  
  plt = ggplot(d, aes(t, m)) + geom_line(stat = "identity")
  plt = plt + geom_ribbon(aes(ymin=m-sdd, ymax=m+sdd), alpha = 0.2)
  plt = plt + ylab("Distance") + xlab("Time")
  plt = plt + ggtitle(title)
  plt  
}

#---

# Sequence:
# make a list with Bjarni, Asas, Trond
person = c("Asas", "Bjarni", "Trond")
filelistname = "dir.txt"
pos.dataset = data.frame()
speed.dataset = data.frame()
first = TRUE
time = seq(0, 3, 0.04)
#make a data frame for everything: [name of person][name of file][hump data with time-trials as rows]
for (name in person){
  
  path = paste("DataCleanup/humpfiles/", name, "/", sep="") 
  #read in file with names of csv files, found in humpfiles/Name/
  files = read.delim(paste(path, filelistname, sep=""), header=FALSE)
  #print(files)

  for (csvfile in files[,1]){
    #csvfile = "Trond_point3_armbage_raw.txt.out.csv"
    #print(csvfile)
    fn = paste(path, csvfile, sep="")
    tmp = read.delim(fn, header=FALSE)
    
    time = tmp[1,] #first row
      
    tmp = tmp[-1,]
    # also save to use for speed, but need to do it 
    speed = t(apply(tmp, 1, diff))
    #speed = t(apply(tmp, 1, SMA, n=4))
    
    #get numer of rows of tmp
    numrows = nrow(tmp)
    speednumrows = nrow(speed)
    tmp = data.frame(tmp)
    speed = data.frame(speed)
    #fill a vector with (name of file - person_): gsub(paste(person, "_",delim=""), "", csvfile)
    removestr = paste(name, "_", "|_raw.txt.out.csv", sep="")
    #print(removestr)
    movement = gsub(removestr, "", csvfile)
    #print(movement)
    movementcol = rep(movement, numrows)  
    #fill another vector with name of person: 
    personcol = rep(name, numrows)
    
    #add vectors as columns to tmp
    #df = data.frame(person=personcol, movement=movementcol, data = tmp)
    df = cbind(personcol, movementcol, tmp)
    
    #calculate derivative for the movement
    personcolspeed = rep(name, speednumrows)
    movementcolspeed = rep(paste(movement, "_speed", sep=""), speednumrows)
    speed.df = cbind(personcol = personcolspeed, movementcol = movementcolspeed, speed)
    
    #add tmp to bottom of main dataframe
    if(first){
      pos.dataset = df
      speed.dataset = speed.df
      first = FALSE
    }
    else{
      # check if number of columns is correct or needs padding
      # too few - add padding on df
      # too many - add padding on dataset
      pos.dataset = rbind.fill(pos.dataset, df)
      speed.dataset = rbind.fill(speed.dataset, speed.df)
      
    }
      
  }
}
pos.dataset = pos.dataset[ , ! apply( pos.dataset , 2 , function(x) all(is.na(x)) ) ]
#find average and std dev of all movements, across persons
#get unique values of movements: 
pos.movements = pos.dataset$movementcol
pos.movements  = pos.movements[!duplicated(pos.movements)]#, or something

speed.dataset = speed.dataset[ , ! apply( speed.dataset , 2 , function(x) all(is.na(x)) ) ]
speed.movements = speed.dataset$movementcol
speed.movements  = speed.movements[!duplicated(speed.movements)]

#--------------- single plot with std dev -----------

dataset = speed.dataset
movements = speed.movements
person = c(person, "all")
print_to_file = TRUE
d = data.frame()
#plots = vector(mode="list", length=90)
for (name in person){
  print(name)
  for (mv in movements){
    #mv = "point3_vinkel_speed"
    print(mv)
    if(name=="all")
      wavedata = subset(dataset, movementcol==mv)
    else
      wavedata = subset(dataset, movementcol==mv & personcol==name)
    wavedata = wavedata[,-(1:2)]
    ctr = 0
    time=0
    for(i in 1:(ncol(wavedata))){
      time[i] = ctr
      ctr = ctr + 0.04
    }
    
    #plot_no_time(wavedata, time, title=mv)
    arg = wavedata
    title = paste(mv, name)
    # remove na values
    arg = arg[ , ! apply( arg , 2 , function(x) all(is.na(x)) ) ]
    mat = t(arg)
    
    #Mat1 = mat[,-1] # index from 2 to end (end to beginning and one back)
    #d = data.frame(t=time[1:nrow(mat)], m=rowMeans(mat), sdd=rowSds(mat))
    tmp.plot = data.frame(name = name, t=time[1:nrow(mat)], m=rowMeans(mat), sdd=rowSds(mat))
    d = rbind.fill(d, tmp.plot)

    plt = ggplot(d, aes(t, m)) + geom_line(stat = "identity")
    #plt = ggplot(d, aes(t, m, color=name)) + geom_line(stat = "identity")
    #plt = plt + geom_ribbon(aes(ymin=m-sdd, ymax=m+sdd, fill=name, linetype=NA), alpha = 0.1)
    plt = plt + ylab("Distance") + xlab("Time")
    plt = plt + ggtitle(title)
    #append(plots, plt)
    
    if(print_to_file){
      png(filename=paste(title, ".png", sep=""))
      print(plt) 
      dev.off()  
    }
    else
      print(plt)
    
  }
}
#multiplot(plots, cols=4)


#--------------- plotting all subjects in one ----

dataset = speed.dataset
movements = speed.movements
stddevstr = "no_std_dev"
usestddev = FALSE
#map.person = list(Asas = "Subject 1", Bjarni="Subject 2", Trond="Subject 3", all = "All")
map.person = c("Subject 1", "Subject 2", "Subject 3", "All")
names(map.person) = c(person, "all")
print_to_file = TRUE
maxmin = data.frame()
movements = Filter(function(x) grepl("hand", x), movements)
#plots = vector(mode="list", length=90)
for (mv in movements){
  #print(mv)
  d = data.frame()
  for (name in names(map.person)){
    #print(name)
    
    #mv = "point3_vinkel_speed"
    
    if(name=="all")
      wavedata = subset(dataset, movementcol==mv)
    else
      wavedata = subset(dataset, movementcol==mv & personcol==name)
    wavedata = wavedata[,-(1:2)]
    ctr = 0
    time=0
    for(i in 1:(ncol(wavedata))){
      time[i] = ctr
      ctr = ctr + 0.04
    }
    
    #plot_no_time(wavedata, time, title=mv)
    arg = wavedata
    title = paste(mv, name, stddevstr)
    # remove na values
    arg = arg[ , ! apply( arg , 2 , function(x) all(is.na(x)) ) ]
    mat = t(arg)
    
    #Mat1 = mat[,-1] # index from 2 to end (end to beginning and one back)
    #d = data.frame(t=time[1:nrow(mat)], m=rowMeans(mat), sdd=rowSds(mat))
    
    tmp.plot = data.frame(Subjects = map.person[name] , t=time[1:nrow(mat)], m=rowMeans(mat), sdd=rowSds(mat))
    d = rbind.fill(d, tmp.plot)
    
    
  }
  #plt = ggplot(d, aes(t, m)) + geom_line(stat = "identity")
  plt = ggplot(d, aes(t, m, color=Subjects))
  plt = plt + geom_line(stat = "identity", size=1.1, aes(linetype=Subjects) )
  plt = plt + scale_linetype_manual(values=c(4,3,2,1))
  if(usestddev)
    plt = plt + geom_ribbon(aes(ymin=m-sdd, ymax=m+sdd, fill=Subjects, color=NA), alpha = 0.2)
  plt = plt + scale_color_manual(values=c("red", "darkgreen", "blue", "black"))
  plt = plt + scale_fill_manual(values=c("red", "darkgreen", "blue", "black"))
  plt = plt + ylab("Distance") + xlab("Time")
  plt = plt + theme(legend.title = element_text(colour="black", size=16, face="bold"))
  plt = plt + theme(legend.text = element_text(colour="black", size = 16, face = "bold"))
  #plt = plt + ggtitle(title)
  #append(plots, plt)
  
  if(print_to_file){
    png(filename=paste(title, ".png", sep=""))
    print(plt) 
    dev.off()  
  }
  else
    print(plt)

  # store max and min values
  if(grepl("hand", mv)){
    sbset = subset(d, Subjects=="All")
    newrow = data.frame(movement=mv, max=max(sbset$m, na.rm=TRUE), min=min(sbset$m, na.rm=TRUE))
    maxmin = rbind(maxmin, newrow)
  }
}
#}



# ------------------------


plotfun(persons = person, movements = speedmovements, dataset = speed.dataset)

plotfun = function (persons, movements, dataset) {
  person = c(persons, "all")
  #plots = vector(mode="list", length=90)
  for (name in person){
    print(name)
    for (mv in movements){
      #mv = "point3_armbage"
      print(mv)
      if(name=="all")
        wavedata = subset(dataset, movementcol==mv)
      else
        wavedata = subset(dataset, movementcol==mv & personcol==name)
      wavedata = wavedata[,-(1:2)]
      ctr = 0
      time=0
      for(i in 1:(ncol(wavedata))){
        time[i] = ctr
        ctr = ctr + 0.04
      }
      
      #plot_no_time(wavedata, time, title=mv)
      arg = wavedata
      title = paste(mv, name)
      # remove na values
      arg = arg[ , ! apply( arg , 2 , function(x) all(is.na(x)) ) ]
      mat = t(arg)
      
      #Mat1 = mat[,-1] # index from 2 to end (end to beginning and one back)
      d = data.frame(t=time[1:nrow(mat)], m=rowMeans(mat), sdd=rowSds(mat))
      
      
      plt = ggplot(d, aes(t, m)) + geom_line(stat = "identity")
      plt = plt + geom_ribbon(aes(ymin=m-sdd, ymax=m+sdd), alpha = 0.2)
      plt = plt + ylab("Distance") + xlab("Time")
      plt = plt + ggtitle(title)
      #append(plots, plt)
      
      #png(filename=paste(title, ".png", sep=""))
      print(plt) 
      #dev.off()
    }
  }
  
}
