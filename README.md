# Minor Histocompatibility Antigen Identification Pipeline (MiHAIP)

Allogeneic hematopoietic stem cell transplantation (alloHSCT) can be a curative therapy for hematological malignancies. This approach cures a significant percentage of patients via graft-versus-leukemia (GVL) effect[1-3]. Unfortunately, the desired GVL effect is often accompanied with undesired graft-versus-host disease (GVHD)[4]. 
![](https://github.com/wwang-nmdp/MiHAIP/blob/master/MiHAIP/Pictures/MiHAandNeoantigens3.png)

After HLA-matched alloHSCT, GVL and GVHD are both mediated by donor-derived T-cells recognizing polymorphic peptides presented by HLA molecules on recipient antigen present cells (APCs).These polymorphic peptides can be tumor-specific antigens (TSAs) or neoantigens, produced by tumor-mediated mutations, mostly contribute GVL effect. It can also be minor histocompatibility antigens (MiHAs), which are produced by genetic differences between donor and recipient and cause GVHD. The expression level and tissue distribution ofMiHA are proved factors in transplant effects, GVL and GVHD.
![](https://github.com/wwang-nmdp/MiHAIP/blob/master/MiHAIP/Pictures/MiHApresentedByHLA2.png)

MiHAs are considered as well as targets for immunotherapy. At least 100 MiHAs with HLA-restriction have been validated across the genome including male-specific, Y chromosome-encoded antigens, which affect sex-mismatched HSCT-related outcomes[5].

Therefore, comprehensive identification of dominant repertoire of MiHA and TSA is a reliable strategy for immunotherapy to augment GVL and prevent GVHD.




## 1. Design of Minor Histocompatibility Antigen Identification Pipeline

The MiHA identification pipeline integrates diverse biological data sources include whole genome sequencing data, HLA genotyping, clinical outcomes,  tissue-specific expression data, known MiHAs data and minor allele frequencies, as well as integrates multiple 3rd party software include RTGtools[6], snpEff[7], netchop3.1[8] and netMHCpan3.0[9] to predict the potential MiHAs by comparing the variants between transplanted donor and recipient.
   **(Note: all the list tools would require individual users to decide their license situation and take proper action)**
   
Meanwhile, the pipeline simulates the antigen processing and presenting on recipients’ cell surface via restricted HLA molecules. For details of pipeline please check the flowchart:
![](https://github.com/wwang-nmdp/MiHAIP/blob/master/MiHAIP/Pictures/MiHAIP_workflow_updated%202.png)

_A workflow to identify MiHAs. Note: Male recipients with female donors were treated separately to analyze variants on the Y chromosome (HY)._





Two genome comparison: RTGtools were applied to compare the whole genome sequencing data between donor and recipient.
The variants’ effect annotation: snpEff was used to annotate the variants’ effects. 
MiHA prediction algorithms: The proteasomal processing prediction by netChop3.1 and MHC class I binding prediction was performed by netMHCpan3
  
  
  
  
## 2. Pre-installation

a. Install `RTGtools` from REAL TIME GENOMICS:

```unix 
wget https://github.com/RealTimeGenomics/rtg-tools/releases/download/3.7.1/rtg-tools-3.7.1-linux-x64.zip
unzip rtg-tools-3.7.1-linux-x64.zip

~/Tools/rtg-tools-3.6.2$ java -jar RTG.jar 


Usage: rtg COMMAND [OPTION]...
rtg RTG_MEM=16G COMMAND [OPTION]...  (e.g. to set maximum memory use to 16 GB)
Type 'rtg help COMMAND' for help on a specific command.
```
b. Install `SnpEff` from http://snpeff.sourceforge.net/index.html  SnpEff is a variant annotation and effect prediction tool. It annotates and predicts the effects of variants on genes (such as amino acid changes).

```unix 
wget http://sourceforge.net/projects/snpeff/files/snpEff_latest_core.zip
unzip snpEff_latest_core.zip
java -jar snpEff.jar
```
#Run 'java -jar snpEff.jar command' for help on each specific command

#Usage: snpEff build [options] genome_version
java -jar snpEff.jar build GRCh38.82
c. Install `netChop3.1` from Center For Biological Sequence Analysis, Technical University of Denmark.
   The NetChop 3.1 may be downloaded only by special agreement.  For academic users there is a download site at:http://www.cbs.dtu.dk/cgi-bin/nph-sw_request?netchop. Other users are requested to contact   software@cbs.dtu.dk.   

```unix 
cat netchop-3.1.<unix>.tar.Z | uncompress | tar xvf -
export NETCHOP=/full/path/to/netchop-3.1
export TMPDIR=/full/path/to/netchop-3.1/tmp
```

In the 'netchop-3.1' directory test the software:
bin/netchop test/test.fsa > test.out
  For research purpose, the net Chop3.1 is pre-built in MiHA identification pipeline. Make sure two environment variables, NETCHOPandTMDIR are set properly.Alsomakesurethat TMPDIR has the sticky bit set (the long listing should render 'drwxrwxrwt...'). If not, set it:

```unix 
chmod 1777 $TMPDIR
```
d. Install `netMHCpan-3.0`
Like netChop, the netMHCpan-3.0 may only be downloaded only by special agreement as well. In addition, it requests to download the data file (data.tar.gz) separately.

```unix 
cat netMHCpan-3.0.<unix>.tar.gz | uncompress | tar xvf -

cd ./netMHCpan-3.0
wget http://www.cbs.dtu.dk/services/NetMHCpan-3.0/data.tar.gz
tar -xvf data.tar.gz
```




## 3. Set environment variables

```unix 
export TMPDIR=/Path/to/Tools/netMHCpan-3.0/tmp
export NMHOME=/Path/to/Tools/netMHCpan-3.0/bin
export NETMHCpan=/Path/to/Tools/netMHCpan-3.0

../netMHCpan -p test.pep > test.pep.myout
../netMHCpan test.fsa > test.fsa.myout
../netMHCpan -hlaseq B0702.fsa test.fsa > test.fsa_userMHC.myout
```


## 4. Build database



cds.db is pre-built hg38 transcriptome and minor allele frequence (version:snp147common) database.

Build you own minor allele frequency database by using lastest versions. 
Go to http://hgdownload.soe.ucsc.edu/goldenPath/hg38/database/ download the SNP dataset. For example, snp147common

```unix 
wget http://hgdownload.soe.ucsc.edu/goldenPath/hg38/database/snp147Common.txt.gz
gunzip snp147Common.txt.gz

java -jar ./MiHAIP1.4.5.jar/Tools/filterFreq.jar snp147Common.txt
```
Generate an output file named filteredFile.txt

Run the freq2db which import the minor allele frequence data from filteredFile.txt into the cds.db

```unix 
java -jar ./MiHAIP1.4.5.jar/Tools/freq2db.jar filteredFile.txt
```


## 5. Install MiHAIP
a. Copy MiHAIP_1.4.5.tar.gz and cds.db from /Volumes/bioxover/users/wwang/MiHAIP_released

```unix 
tar -xvzf MiHAIP_1.4.5.tar.gz
```

b. Read documentation
```unix 
java -jar MiHAIP1.4.5.jar -help
```

## 6. Test the Pipeline by Sample Data
For testing the MiHA identification pipeline, you could use a pair of sample data from: /Volumes/bioxover/users/wwang/MiHAIP_released
Copy all the executable programs (netChop-3.1, netMHCpan-3.0, RTG.jar and snpEff.jar) into the directory ./MiHAIP1.4.5/Tools.
Copy the input files into ./path/to/input. Caution: the input files should include the .vcf.gz and index file .vcf.gz.tbi


## Note: 
Assign a unique pair ID for each run, e.g. -pid 1; 
The sex needs to be defined donor's sex follow recipient's sex, e.g. -sex MM, which means male donor to male recipient. It helps to find Y chromosome encoded MiHAs (H-Y). 
The HLA types should include all 6 alleles and separated by comma (there is no space in between alleles).
-t argument points the path to tools box.
Set the directory of output files by using -o   
cp /Volumes/bioxover/users/wwang/MiHAIP_released/*vcf* /path/to/your/input/directory


```unix 
java -jar MiHAIP0.3.3.jar -d /path/to/input/Donor.vcf.gz -r /path/to/input/Recipient.vcf.gz -pid 1 -sex MM -hla A02:01,A02:01,B07:02,B14:02,C07:02,C08:02 -t /path/to/Tools/ -o /path/to/output
```

The program takes ~5min run through sample data ( average 22min for a whole genome), then check the predicted results from ./path/to/output/predicted 





## References:
[1] Copelan, E.A., Hematopoietic stem-cell transplantation. N Engl J Med, 2006. 354(17): p. 1813-26.


[2]	Horowitz, M.M., et al., Graft-versus-leukemia reactions after bone marrow transplantation. Blood, 1990. 75(3): p. 555-62.


[3]	Miller, J.S., et al., NCI First International Workshop on The Biology, Prevention, and Treatment of Relapse After Allogeneic Hematopoietic Stem Cell Transplantation: Report from the Committee on the Biology Underlying Recurrence of Malignant Disease following Allogeneic HSCT: Graft-versus-Tumor/Leukemia Reaction. Biol Blood Marrow Transplant, 2010. 16(5): p. 565-86.


[4]	Ferrara, J.L., et al., Graft-versus-host disease. Lancet, 2009. 373(9674): p. 1550-61.


[5]	Gooley, T.A., et al., Reduced mortality after allogeneic hematopoietic-cell transplantation. N Engl J Med, 2010. 363(22): p. 2091-101.


[6] Cleary, J.G., et al., Joint variant and de novo mutation identification on pedigrees from high-throughput sequencing data. J Comput Biol, 2014. 21(6): p. 405-19.


[7]	Cingolani, P., et al., A program for annotating and predicting the effects of single nucleotide polymorphisms, SnpEff: SNPs in the genome of Drosophila melanogaster strain w1118; iso-2; iso-3. Fly (Austin), 2012. 6(2): p. 80-92.


[8]	Kesmir, C., et al., Prediction of proteasome cleavage motifs by neural networks. Protein Eng, 2002. 15(4): p. 287-96.


[9]	Nielsen, M. and M. Andreatta, NetMHCpan-3.0; improved prediction of binding to MHC class I molecules integrating information from multiple receptor and peptide length datasets. Genome Med, 2016. 8(1): p. 33.
