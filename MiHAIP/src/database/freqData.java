package database;

public class freqData {
	String [] data = new String[4];
	
	public freqData(String base, String freq){
		for(int i = 0; i<4; i++){
			data[i] = "0";
		}
		String[] baseData = base.split("/");
		String[] freqData = freq.split(",");
		if(baseData.length == freqData.length){
			for(int j = 0; j< baseData.length; j++){
				switch(baseData[j]){
					case "A": data[0] = freqData[j];
					break;
					case "T": data[1] = freqData[j];
					break;
					case "C": data[2] = freqData[j];
					break;
					case "G": data[3] = freqData[j];
					break;
					
				}
			}
		}else{
			for(int j = 0; j< baseData.length; j++){
				switch(baseData[j]){
					case "A": data[0] = freq;
					break;
					case "T": data[1] = freq;
					break;
					case "C": data[2] = freq;
					break;
					case "G": data[3] = freq;
					break;
				}
			}
		}
	}
	
	public String getAfreq(){
		return data[0];
	}
	
	public String getTfreq(){
		return data[1];
	}
	
	public String getCfreq(){
		return data[2];
	}
	
	public String getGfreq(){
		return data[3];
	}
}
