using System;
using System.Collections.Generic;

namespace hackerearth
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			//Console.WriteLine ("Hello World!");
			GetUserData ();
		}

		static void GetUserData()
		{
			string inputFistLine = Console.ReadLine();
			string[] words = inputFistLine.Split(' ');

			int numDays = Convert.ToInt32 (words [0]);
			int numUsers=Convert.ToInt32 (words [1]);
			int numProducts= Convert.ToInt32 (words [2]);

			int[] products = new int[numProducts];
			int[] totalRating = new int[numProducts];
			int[] userLiked = new int[numProducts];

			int[] maxRatingEachDay = new int[numDays];
			int[] maxLikedProductEachday = new int[numDays]; 


			int[,] productCountEachDay = new int[numProducts,numDays];

			for (int day = 0 ; day <  numDays ; day++)
			{
				string readInputEachDay = Console.ReadLine();
				int numRecords = Convert.ToInt32 (readInputEachDay);
				Dictionary<int, int> dt = new Dictionary<int, int>();

				Boolean startflag= true;
				for(int j = 0 ; j <  numRecords ; j++){
					string row = Console.ReadLine();
					string[] records = row.Split(' ');
					int x= Convert.ToInt32 (records [0]);
					int y = Convert.ToInt32 (records [1]);
					int a = Convert.ToInt32 (records [2]);

					if (x > numUsers || x <= 0) {
						continue;
					}

					if (y > numProducts || y <= 0) {
						continue;
					}

					totalRating[y-1]= totalRating[y-1]+a;
					userLiked[y-1]= userLiked[y-1] +1;

					int previouRating=0;
					bool hasValue = dt.TryGetValue(y-1, out previouRating);


					if (dt.ContainsKey(y-1)) {						
						dt [y - 1] = dt [y - 1] + a;
					} else {
						// do something when the value is not there
						dt.Add(y-1,a);
					}

					if(startflag){
						maxRatingEachDay[day]= a;
						maxLikedProductEachday[day]= y-1;
						startflag = false;
					}

					productCountEachDay [y - 1,day] = productCountEachDay [y - 1,day] + 1;
					dt.TryGetValue(y, out previouRating);
					previouRating= dt[y-1];
					if (previouRating > maxRatingEachDay [day]) {
						//if same rating choose product with lesser index.
						maxRatingEachDay [day] = previouRating;
						maxLikedProductEachday [day] = y - 1;
					

					} else if (previouRating == maxRatingEachDay [day]) {
						if ((y - 1) < maxLikedProductEachday [day]) {

							maxRatingEachDay [day] = previouRating;
							maxLikedProductEachday [day] = y - 1;
						}	
					} else {
						//do Nothing
					}



				}
			}

			//finding average rating and maximum hit count
			int maximumAvgIndex=0 ;
			double averageMax;
			if (totalRating [0] == 0 || userLiked [0] == 0) {
				averageMax = 0;
			} else {
				averageMax=  (double)totalRating[0]/userLiked[0];
			}


			int maxLikedIndex=0;
			int maxLikes= userLiked[0];
			for (int j = 0 ; j< numProducts ; j++){
				double currentAvgrating ;
				//double currentAvgrating =  (double)totalRating[j]/userLiked[j];
				if (totalRating [j] == 0 || userLiked [j] == 0) {
					currentAvgrating = 0;
				} else {
					currentAvgrating=  (double)totalRating[j]/userLiked[j];
				}
				int currLikes =  userLiked[j];

				if(currLikes > maxLikes){
					maxLikes= currLikes; 
					maxLikedIndex = j ;
				}

				if(currentAvgrating > averageMax){
					averageMax= currentAvgrating; 
					maximumAvgIndex = j ;
				}

				
			}

			Console.WriteLine(maximumAvgIndex+1 +" "+ (maxLikedIndex+1));
			//Console.Write((maxLikedProductEachday[0]+1));
			for (int productID = 0 ; productID< numProducts ; productID++){

				int maxCount= productCountEachDay[productID,numDays-1];
				int productMaxday = numDays - 1;
				for (int index = numDays - 1; index >= 0; index--) {
					int currMaxCount = productCountEachDay [productID,index];
					if (currMaxCount >= maxCount) {
						productMaxday = index;
						maxCount = currMaxCount;
					}
				
				}
				Console.Write((productMaxday+1)+ " ");
			}

		}   

		static void useArray(){
			int [] marks = new int[5]  { 99,  98, 92, 97, 95};

		}

		static void useDict(){

			Dictionary<int, string> dt = new Dictionary<int, string>();
			dt.Add(1, "One");
			dt.Add(2, "Two");
			dt.Add(3, "Three");
			foreach (KeyValuePair<int, String> kv in dt)
			{
				Console.WriteLine(kv.Key + " " + kv.Value);
			}
		}





	}
}

