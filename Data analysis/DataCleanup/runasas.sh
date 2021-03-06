#!/bin/bash
SAVEIFS=$IFS
IFS=$("\n")
INDIR="/Users/trondarildtjstheim/Documents/Master CogSci/Neuro-modelling, cognitive robotics & agents /Project - robot animation/MAMN10_G8_data-program/Data analysis/DataCleanup/rawdata/Asas/*.txt"

OUTDIR="/Users/trondarildtjstheim/Documents/Master CogSci/Neuro-modelling, cognitive robotics & agents /Project - robot animation/MAMN10_G8_data-program/Data analysis/DataCleanup/humpfiles"


cd bin
for f in $INDIR
do
	java CleanupMain -s "$f"
	#echo $f
done
cd ../rawdata/Asas
mv *.csv ../../humpfiles/Asas
cd ../../humpfiles/Asas
ls > dir.txt
sed -i "" '$ d' dir.txt
IFS=$SAVEIFS
