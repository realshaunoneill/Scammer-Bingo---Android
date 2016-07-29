#include <iostream.h>
#include <conio.h>
#include <windows.h>

//Scammer Bingo CLI Edition Coded in Borland C++ 5.00
int main()
{

	SetConsoleTitle("Scammer Bingo by AK668(Original Idea:Jim Browning)");
   char s[]={'x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x'};
   int col[]={12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12};
   char c=' ';
   

   float m =0;

   do
   {

   	clrscr();
      switch(c)
      {
      	case '1':
         case '!':
         	s[0]='y';
            col[0]=10;
            m++;
            break;
         case '2':
         case '@':
         	s[1]='y';
            col[1]=10;
            m++;
            break;
         case '3':
         case '#':
         	s[2]='y';
            col[2]=10;
            m++;
            break;
         case '4':
         case '$':
         	s[3]='y';
            col[3]=10;
            m++;
            break;
         case '5':
         case '%':
         	s[4]='y';
            col[4]=10;
            m++;
            break;
         case '6':
         case '^':
         	s[5]='y';
            col[5]=10;
            m++;
            break;
         case '7':
         case '&':
         	s[6]='y';
            col[6]=10;
            m++;
            break;
         case '8':
         case '*':
         	s[7]='y';
            col[7]=10;
            m++;
            break;
         case '9':
         case '(':
         	s[8]='y';
            col[8]=10;
            m++;
            break;
         case 'q':
         case 'Q':
         	s[9]='y';
            m++;
            col[9]=10;
            break;
         case 'w':
         case 'W':
         	s[10]='y';
            m++;
            col[10]=10;
            break;
         case 'e':
         case 'E':
         	s[11]='y';
            col[11]=10;
            m++;
            break;
         case 'r':
         case 'R':
         	s[12]='y';
            m++;
            col[12]=10;
            break;
         case 't':
         case 'T':
         	s[13]='y';
            col[13]=10;
            m++;
            break;
         case 'y':
         case 'Y':
         	s[14]='y';
            col[14]=10;
            m++;
            break;
         case 'u':
         case 'U':
         	s[15]='y';
            col[15]=10;
            m++;
            break;
         case 'z':
         case 'Z':
         	for(int i=0;i<16;i++){
         		s[i]='x';
               col[i]=12;

            }
            m=0;
            clrscr();
            cout <<"Everthing is reset\nPress any key to continue...\n";
            getch();
            clrscr();
            break;
      }
   	textcolor(GREEN);
   	cprintf("Scammer Bingo CLI Edition(for keyboarders) ver 0.1(Thanks to Community for inspiration)");
      cout<<endl;
      textcolor(12);

      cprintf("Attention:This program immediately responses to key presses");cout<<endl;
      cprintf("If you screw up in button click or want to restart press the [z/Z] key to reset.");

      cout <<endl;
      cprintf("To check enter the key marked in square brackets.");cout <<endl;
      cprintf("To quit press X key or press Ctrl-C(make sure not to accidentally hit X :D)");
      cout<<endl;
      cout<<"[1].Eventvwr[";
      textcolor(col[0]);
      cprintf("%c",s[0]);
      cout<<"]"<<"\t";

      cout<<"[2].SecureServer[";
      textcolor(col[1]);
      cprintf("%c",s[1]);
      cout<<"]"<<endl;

      cout<<"[3].Tree[";
      textcolor(col[2]);
      cprintf("%c",s[2]);

      cout<<"]"<<"\t";
      cout<<"[4].cmd[";
      textcolor(col[3]);
      cprintf("%c",s[3]);

      cout<<"]"<<"\n";
      cout<<"[5].netstat[";
      textcolor(col[4]);
      cprintf("%c",s[4]);

      cout<<"]"<<"\t";
      cout<<"[6].msconfig[";
      textcolor(col[5]);
      cprintf("%c",s[5]);
      cout<<"]"<<"\n";

      cout<<"[7].support.me[";
      textcolor(col[6]);
      cprintf("%c",s[6]);
      cout<<"]"<<"\t";
      cout<<"[8].Corrupted Drivers[";
      textcolor(col[7]);
      cprintf("%c",s[7]);

      cout<<"]"<<"\n";

      cout<<"[9].Stopped Services[";
      textcolor(col[8]);
      cprintf("%c",s[8]);
      cout<<"]"<<"\t";
      cout<<"[q/Q].Network Security[";
      textcolor(col[9]);
      cprintf("%c",s[9]);

      cout<<"]"<<"\n";
      cout<<"[w/W].Microsoft Certified[";
      textcolor(col[10]);
      cprintf("%c",s[10]);
      cout<<"]"<<"\t";
      cout<<"[e/E].Syskey[";
      textcolor(col[11]);
      cprintf("%c",s[11]);

      cout<<"]"<<"\n";
      cout<<"[r/R].hh h[";
      textcolor(col[12]);
      cprintf("%c",s[12]);

      cout<<"]"<<"\t";
      cout<<"[t/T].Speaks some Gibberish[";
      textcolor(col[13]);
      cprintf("%c",s[13]);

      cout<<"]"<<"\n";
      cout<<"[y/Y].Run BOX[";
      textcolor(col[14]);
      cprintf("%c",s[14]);
      cout<<"]"<<"    ";
      cout<<"[u/U].$$$(huge amt of cash and money)[";
      textcolor(col[15]);
      cprintf("%c",s[15]);
      cout<<"]"<<endl;
      if(m > 8&& m<10)
      {
       	cout <<"Conclusion:Half way through a scam!"<<endl;
      }
      else if(m==16)
      	cout <<"Scammer Verified! >:D\n";
      else if(s[15]=='y')
      	cout <<"Conclusion:It Ends Here!Text Book Scam!"<<endl;
      else if (m==0)
      {
       	cout <<"Aint A scam yet :("<<endl;
      }




      cout<<"Percentage status:[";

      for(int p =0;p<m;p++)
      {

         cout <<"*";

      }
      cout<<"]"<<(m/16)*100<<'%'<<endl;
      if(m>16)
      {
      	m = 0;
         clrscr();
         cout <<"You Broke it >:(.\nPress any key to reset...";
         getch();
         for(int i=0;i<16;i++){
         		s[i]='x';
               col[i]=12;

            }
      }


      c =(char)getch();


   }
   while(c!='x'&&c!='X');
   clrscr();
   textcolor(12);
   cprintf("Thank you for using SCB CLI edition.");
   textcolor(9);
   cprintf("Credit goes to Jim Browning for the original Idea ");
   cout <<endl;
   textcolor(10);
   cprintf("Have a nice Day :D");
   getch();
  return 0;
}
