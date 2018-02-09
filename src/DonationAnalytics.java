
import java.io.*;
import java.util.*;
/*
 * the analyzer class
 * */
public class DonationAnalytics {
	
	//the hashtable for storing:key cmte_id#zip#year value: a list of contributions
	//the reason why I do this is only cmte_id and zip and year together can indicate whether a donor is repeat
	Map<String,List<Integer>> table=new HashMap<String,List<Integer>>();
	//the hashset for storing all of the donor have seen
	Set<String> denotor_pool=new HashSet<String>();
	/*
	 * below the functions used to detect whether the record is valid
	 * */
	public boolean isValidOtherId(String input){
		if(input.length()==0){
			return true;
		}
		return false;
	}
	public boolean isValidCMTEID(String input){
		if(input.length()!=9){
			return false;
		}
		if(input.charAt(0)<'A' && input.charAt(0)>'Z'){
			return false;
		}
		for(int i=1;i<9;i++){
			if(input.charAt(i)>='0' && input.charAt(i)<='9'){
				continue;
			}
			else{
				return false;
			}
		}
		return true;
	}
	public boolean isValidName(String input){
		 if(input.length()==0){
			 return false;
		 }
		 else{
			 return true;
		 }
	}
	public boolean isValidZip(String input){
		if(input.length()<5){
			return false;
		}
		for(int i=0;i<input.length();i++){
			if(input.charAt(i)>='0' && input.charAt(i)<='9'){
				continue;
			}
			else{
				return false;
			}
		}
		return true;
	}
	public boolean isValidAmount(String input){
		 if(input.length()==0 || input.length()>=10){
			 return false;
		 }
		 for(int i=0;i<input.length();i++){
			 if(input.charAt(i)>='0' && input.charAt(i)<='9'){
				 continue;
			 }
			 else{
				 return false;
			 }
		 }
		 return true;
	}
	public boolean isValidDate(String input){
		if(input.length()!=8){
			return false;
		}
		for(int i=0;i<8;i++){
			 if(input.charAt(i)<'0' || input.charAt(i)>'9'){
				 return false;
			 } 
		}
		int year=Integer.parseInt(input.substring(4,8));
		int month=Integer.parseInt(input.substring(0,2));
		int day=Integer.parseInt(input.substring(2,4));
		if(year>2018||day>31||month>12){
			return false;
		}
		return true;
	}
	public boolean isValidRecord(String[] tuple){
		 return isValidOtherId(tuple[15]) && isValidCMTEID(tuple[0]) 
				 &&isValidName(tuple[7]) && isValidZip(tuple[10])
				 &&isValidAmount(tuple[14]) && isValidDate(tuple[13]);
				
	}
	/*
	 * program runtime framework
	 * */
	public void runSolution(String inputpath1,String inputpath2,String outputpath){
	    /*
	     * parse the file line by line
	     * */
		 File filep=new File(inputpath2);
		 int percentile=0;
		 File file=new File(inputpath1);
		 BufferedReader buf_reader=null;
		 BufferedWriter buf_writer=null;
		 String line=null;
		 try{
			 buf_writer=new BufferedWriter(
					   new OutputStreamWriter(new FileOutputStream(
							   new File(outputpath),true
							   )
							   )
					 );
			 buf_reader=new BufferedReader(new FileReader(filep));
			 line=buf_reader.readLine();
			 percentile=Integer.parseInt(line);
			 buf_reader.close();
			 System.out.println(percentile);
			 buf_reader=new BufferedReader(new FileReader(file));
			 while((line=buf_reader.readLine())!=null){
				  String[] tuple=line.split("\\|");
				  if(isValidRecord(tuple)==false){
					  continue;
				  }
				  else{
					  
					  String cmte_id=tuple[0];
					  String name=tuple[7];
					  String zip=tuple[10].substring(0, 5);
					  String date=tuple[13].substring(4, 8);
					  Integer amount=Integer.parseInt(tuple[14]);
					
					  //first we update the repeat table
					  if(table.containsKey(cmte_id+"#"+zip+"#"+date)==false){
						   table.put(cmte_id+"#"+zip+"#"+date, new ArrayList<Integer>());
					  }
					  //not a repeated denotor
					  if(denotor_pool.contains(name+"#"+zip)==false){
						  denotor_pool.add(name+"#"+zip);	  
					  }
					  else{
						  //is a repeated denotor
						  System.out.println("find a repeat denotor");
						  table.get(cmte_id+"#"+zip+"#"+date).add(amount);
						  int repeatSize=table.get(cmte_id+"#"+zip+"#"+date).size();
						  int sum=0;
						  for(int temp:table.get(cmte_id+"#"+zip+"#"+date)){
							  sum+=temp;
						  }
						  int index=1;
						  List<Integer> templist=table.get(cmte_id+"#"+zip+"#"+date);
						  Collections.sort(templist);
						  float pos=(float)(percentile/100.0)*templist.size();
						  int IntegerPart=(int)(pos);
						  if(IntegerPart==0){
							  index=1;
						  }
						  else{
							  float FloatPart=pos-IntegerPart;
							  FloatPart=FloatPart*10;
							  if(FloatPart>=5){
								  IntegerPart++;
							  }
							  index=IntegerPart;
						  }
						  int percentileNum=templist.get(index-1);
						  System.out.println(cmte_id+"|"+zip+"|"+date+"|"+sum+"|"+percentileNum+"|"+repeatSize);
						  String content=cmte_id+"|"+zip+"|"+date+"|"+sum+"|"+percentileNum+"|"+repeatSize+"\n";
						  buf_writer.write(content);
					  }
					  
					
				  }
			 }
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 finally{
			 try {
				buf_writer.close();
				buf_reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
	public static void main(String[] args){
		 DonationAnalytics solution=new DonationAnalytics();
		 solution.runSolution(args[0],args[1],args[2]);
		 
	}

}
