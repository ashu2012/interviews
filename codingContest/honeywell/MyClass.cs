using System;

namespace honeywell
{
	public class MyClass
	{
		public MyClass ()
		{
			
		}

		public static void Main(string[] args)
		{
			MyClass test = new MyClass ();
			GetUserData ();
			Console.WriteLine("Hello {0}", test);
		}
		static void GetUserData()
		{
			Console.Write("please enter your name: ");
			string userName = Console.ReadLine();
			Console.Write("please enter your age: ");
			string userAge = Console.ReadLine();

			List<int> list = new List<int>();
			list.Add(userAge);
			Console.WriteLine(list.Count); // 3

			Console.WriteLine("hello {0}! Your are {1} years old.", userName, list.IndexOf(23));


		}   
	}
}

