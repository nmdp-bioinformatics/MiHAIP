package util;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

import nmdp.Configure;

/**
 * Created by wwang on 10/19/16.
 */
public class FileHelp {
    public static String getOutput() {
        return getRoot() + "/output/";
    }

    public static String getRoot() {
     return Configure.getOutputFolder();
    }

    public static String getMetaFilePath()  {
        return getOutput() + "meta/";
    }

    public static String getChopFilePath() {
        return getRoot() + "Chopped/";
    }

    public static String getDNAfilePath() {
        return getOutput() + "DNA/";
    }

    public static String getProteinPath() {
        return getOutput() + "protein/";
    }
    public static String getYproteinPath() {
        return getOutput() + "Yprotein/";
    }
    
    public static String getYproteinFile(){
    	return getYproteinPath() + Configure.getPairID()+"yProtein.txt";
    }
    
    public static void makeFolders(){
    	makeFolder(getOutput());
    	makeFolder(getMetaFilePath());
    	makeFolder(getDNAfilePath());
    	makeFolder(getProteinPath());
    	makeFolder(getOutput() + "/predicted");
    	makeFolder(getOutput() + "/annotated");
    	makeFolder(getOutput() + "/compared");
    	makeFolder(getOutput() + "/missense");
    	makeFolder(getOutput() + "/cleavaged");
    	makeFolder(getOutput() + "/Ycleavaged");
    	makeFolder(getOutput() + "/chopMeta");
    	makeFolder(getOutput() + "/affinity");
    	makeFolder(getOutput() + "/chopped");
    }

    private static void makeFolder(String name){
    	File folder = new File(name);
    	folder.mkdirs();
    }

    public static String getFnPath() throws URISyntaxException {
        return getRoot()+"./DtoR/msv/fn";
    }

    public static String getFpPath() throws URISyntaxException {
        return getRoot()+"./DtoR/msv/fp";
    }
    
    public static String getVcfevalPath(){
    	return getOutput() + "/compared/" +Configure.getPairID()+".out";
    }
    
    public static String getSnpeffInputFn(){
    	return getVcfevalPath() + "/fn.vcf.gz";
    }
    
    public static String getSnpeffInputFp(){
    	return getVcfevalPath() + "/fp.vcf.gz";
    }
    
    public static String getAnnotateOutputFn(){
    	return getOutput() + "/annotated/" + Configure.getPairID() + "_fn_ann.vcf";
    }
    
    public static String getAnnotateOutputFp(){
    	return getOutput() + "/annotated/" + Configure.getPairID() + "_fp_ann.vcf";
    }
    
    public static String getMissenseFn(){
    	return getOutput() + "/missense/" + Configure.getPairID() + "_fn_ann_msv.vcf";
    }
    public static String getMissenseFp(){
    	return getOutput() + "/missense/" + Configure.getPairID() + "_fp_ann_msv.vcf";
    }
    
    public static String getMergeOutput(){
    	return FileHelp.getProteinPath() + Configure.getPairID() + "_protein.fasta";
    }
    
    public static String getRecipientProtein(){
    	return FileHelp.getProteinPath() + Configure.getPairID() + "_recipient_protien.fasta";
    }
    
    public static String getProteinSlice(){
    	return FileHelp.getProteinPath() + Configure.getPairID() + "_protein_slice.pep";
    }
    public static String getProteinSliceMeta(){
    	return FileHelp.getProteinPath() + Configure.getPairID() + "_protein_slice_meta.fasta";
    }
    
    public static String getDonorProtein(){
    	return FileHelp.getProteinPath() + Configure.getPairID() + "_donor_protien.fasta";
    }
    
    public static String getMetaData(){
    	return FileHelp.getMetaFilePath() + Configure.getPairID() + "_meta.txt";
    }
    
    public static String getCleavageFile(){
    	return getOutput() + "cleavaged/" + Configure.getPairID() + "_cleavaged.txt";
    }
    public static String getYCleavageFile(){
    	return getOutput() + "Ycleavaged/" + Configure.getPairID() + "_cleavaged.txt";
    }
    
    public static String getCleavageYFile(){
    	return getOutput() + "cleavaged/" + Configure.getPairID() + "_y_cleavaged.txt";
    }
    
    public static String getChopFile(){
    	return getOutput() + "chopped/" + Configure.getPairID() + "_chopped.pep";
    }
    
    public static String getChopMetaData(){
    	return getOutput() + "chopMeta/" + Configure.getPairID() + ".txt";
    }
    
    public static String getAffFile_A1(){
    	return getOutput() + "affinity/" + Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_a1)+getPos(Configure.hasTwoHla_a()) +".txt";
    }
    
    public static String getAffFile_A2(){
    	return getOutput() + "affinity/" + Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_a2)+getPos(Configure.hasTwoHla_a()) +".txt";
    }
    
    public static String getAffFile_B1(){
    	return getOutput() + "affinity/" + Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_b1)+getPos(Configure.hasTwoHla_a()) +".txt";
    }
    public static String getAffFile_B2(){
    	return getOutput() + "affinity/" + Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_b2)+getPos(Configure.hasTwoHla_b()) +".txt";
    }
    public static String getAffFile_C1(){
    	return getOutput() + "affinity/" + Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_c1)+getPos(Configure.hasTwoHla_b()) +".txt";

    }
    public static String getAffFile_C2(){
    	return getOutput() + "affinity/" + Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_c2)+getPos(Configure.hasTwoHla_c()) +".txt";

    }

	public static String getPredictFileA1() {
		return getOutput() + "predicted/"+ Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_a1)+getPos(Configure.hasTwoHla_a()) +".txt";
	}
	
	public static String getPredictFileA2() {
		return getOutput() + "predicted/"+ Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_a2)+getPos(Configure.hasTwoHla_a()) +".txt";
	}
	
	public static String getPredictFileB1() {
		return getOutput() + "predicted/"+ Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_b1)+getPos(Configure.hasTwoHla_b()) +".txt";
	}
	public static String getPredictFileB2() {
		return getOutput() + "predicted/"+ Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_b2)+getPos(Configure.hasTwoHla_b()) +".txt";
	}
	
	public static String getPredictFileC1() {
		return getOutput() + "predicted/"+ Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_c1)+getPos(Configure.hasTwoHla_c()) +".txt";
	}
	
	public static String getPredictFileC2() {
		return getOutput() + "predicted/"+ Configure.getPairID() + "_"+ Configure.removeComma(Configure.hla_c2)+getPos(Configure.hasTwoHla_c()) +".txt";
	}
	
	
	private static String getPos(boolean two){
		if(two){
			return "HET";
		}else{
			return "HM";
		}
	}
}
