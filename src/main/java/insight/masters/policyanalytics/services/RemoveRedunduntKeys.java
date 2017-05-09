package insight.masters.policyanalytics.services;

import java.util.ArrayList;
import java.util.List;

public class RemoveRedunduntKeys {
	 String[] arr = {"w10","w20","w10","w30","w20","w40","w50","w50"};
     List<String> arrList = new ArrayList<String>();
     int cnt= 0;
//       List<String> arrList = Arrays.asList(arr);
       List<String> lenList = new ArrayList<String>();
          for(int i=0;i<arr.length;i++){
        for(int j=i+1;j<arr.length;j++){
           if(arr[i].equals(arr[j])){
             cnt+=1;
           }                
        }
        if(cnt<1){
          arrList.add(arr[i]);
        }
          cnt=0;
        }

for(int k=0;k<arrList.size();k++){
            System.out.println("Array without Duplicates: "+arrList.get(k));
        }
}
}
