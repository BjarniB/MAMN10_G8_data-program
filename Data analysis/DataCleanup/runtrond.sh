#!/bin/bash
SAVEIFS=$IFS
IFS=$("\n")
INDIR="/Users/trondarildtjstheim/Documents/Master CogSci/Neuro-modelling, cognitive robotics & agents /Project - robot animation/MAMN10_G8_data-program/Data analysis/DataCleanup/rawdata/Trond/*.txt"

OUTDIR="/Users/trondarildtjstheim/Documents/Master CogSci/Neuro-modelling, cognitive robotics & agents /Project - robot animation/MAMN10_G8_data-program/Data analysis/DataCleanup/humpfiles"


cd bin
for f in $INDIR
do
	java CleanupMain -s "$f"
	#echo $f
done
cd ../rawdata/Trond
mv *.csv ../../humpfiles/Trond
cd ../../humpfiles/Trond
ls > dir.txt
sed -i "" '/dir.txt/d' dir.txt
IFS=$SAVEIFS
