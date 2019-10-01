package com.carleton;

import java.io.Serializable;
import java.util.Arrays;

public class calculateScore implements Serializable {

	public static int CalculateFullHouse( Dice[] myDice )
	{
	  int Sum = 0;

	  int[] i = new int[5];

	  i[0] = myDice[0].getFace();
	  i[1] = myDice[1].getFace();
	  i[2] = myDice[2].getFace();
	  i[3] = myDice[3].getFace();
	  i[4] = myDice[4].getFace();

	  Arrays.sort(i);

	  if( (((i[0] == i[1]) && (i[1] == i[2])) && // Three of a Kind
	       (i[3] == i[4]) && // Two of a Kind
	       (i[2] != i[3])) ||
	      ((i[0] == i[1]) && // Two of a Kind
	       ((i[2] == i[3]) && (i[3] == i[4])) && // Three of a Kind
	       (i[1] != i[2])) )
	  {
	    Sum = 25;
	  }

	  return Sum;
	}
	public static int CalculateOfAKind(Dice[] myDice, int num)
	{
	  int Sum = 0;

	  Boolean OfAKind = false;

	  for( int i = 1; i <= 6; i++ )
	  {
	    int Count = 0;
	    for( int j = 0; j < myDice.length; j++ )
	    {
	      if( myDice[j].getFace() == i )
	        Count++;

	      if( Count >= num )
	        OfAKind = true;
	    }
	  }

	  if( OfAKind )
	  {
	    for( int k = 0; k < myDice.length; k++ )
	    {
	      Sum += myDice[k].getFace();
	    }
	  }

	  return Sum;
	}
	public static int CalculateSmallStraight( Dice[] myDice )
	{
	  int Sum = 0;

	  int[] i = new int[5];

	  i[0] = myDice[0].getFace();
	  i[1] = myDice[1].getFace();
	  i[2] = myDice[2].getFace();
	  i[3] = myDice[3].getFace();
	  i[4] = myDice[4].getFace();

	  Arrays.sort(i);

	  // Problem can arise hear, if there is more than one of the same number, so
	  // we must move any doubles to the end
	  for( int j = 0; j < 4; j++ )
	  {
	    int temp = 0;
	    if( i[j] == i[j+1] )
	    {
	      temp = i[j];

	      for( int k = j; k < 4; k++ )
	      {
	        i[k] = i[k+1];
	      }

	      i[4] = temp;
	    }
	  }

	  if( ((i[0] == 1) && (i[1] == 2) && (i[2] == 3) && (i[3] == 4)) ||
	      ((i[0] == 2) && (i[1] == 3) && (i[2] == 4) && (i[3] == 5)) ||
	      ((i[0] == 3) && (i[1] == 4) && (i[2] == 5) && (i[3] == 6)) ||
	      ((i[1] == 1) && (i[2] == 2) && (i[3] == 3) && (i[4] == 4)) ||
	      ((i[1] == 2) && (i[2] == 3) && (i[3] == 4) && (i[4] == 5)) ||
	      ((i[1] == 3) && (i[2] == 4) && (i[3] == 5) && (i[4] == 6)) )
	  {
	    Sum = 30;
	  }

	  return Sum;
	}
	public static int CalculateYahtzee( Dice[] myDice )
	{
	  int Sum = 0;

	  for( int i = 1; i <= 6; i++ )
	  {
	    int Count = 0;
	    for( int j = 0; j < 5; j++ )
	    {
	      if( myDice[j].getFace() == i )
	        Count++;

	      if( Count > 4 )
	        Sum = 50;
	    }
	  }

	  return Sum;
	}
	public static int AddUpChance( Dice[] myDice )
	{
	  int Sum = 0;

	  for( int i = 0; i < 5; i++ )
	  {
	    Sum += myDice[i].getFace();
	  }

	  return Sum;
	}
	public static int CalculateLargeStraight( Dice[] myDice )
	{
	  int Sum = 0;

	  int[] i = new int[5];

	  i[0] = myDice[0].getFace();
	  i[1] = myDice[1].getFace();
	  i[2] = myDice[2].getFace();
	  i[3] = myDice[3].getFace();
	  i[4] = myDice[4].getFace();

	  Arrays.sort(i);

	  if( ((i[0] == 1) &&
	       (i[1] == 2) &&
	       (i[2] == 3) &&
	       (i[3] == 4) &&
	       (i[4] == 5)) ||
	      ((i[0] == 2) &&
	       (i[1] == 3) &&
	       (i[2] == 4) &&
	       (i[3] == 5) &&
	       (i[4] == 6)) )
	  {
	    Sum = 40;
	  }

	  return Sum;
	}
}
