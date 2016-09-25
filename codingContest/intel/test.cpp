#include <iostream>
#include <map>
#include <vector>
using namespace std;




int main()
{
 
    memset(cumBIT,0,sizeof(cumBIT));
    int n,k; // n length and k characters taking one and more
    cin>>n>>k;
    vector<int>  values=vector<int>(n*k,0); //111222  , n=3, k=2, find all sorted using dp
   for(int i=0;i<k;i++) {
	for(int j=0;j<n;j++) {	
		values[i+j]=i+1; 
	}
   }

   
   dp[0][1]=1;
   for(int i=1;i<values.size();i++) {
     dp[i][1]=1;
     for(int k=2;k<=maxk;k++) {
      for(int j=0;j<i;j++) {
         if(values[j]<values[i]) {
            dp[i][k]+=dp[j][k-1];
         }
      }
     }
     ret+=dp[i][maxk];
   }
   return ret;
 
    cout<<ret<<" compare "<< dp_result(temp,k)<<endl;
 
}
 
