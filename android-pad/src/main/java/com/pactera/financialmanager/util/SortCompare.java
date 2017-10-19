package com.pactera.financialmanager.util;

import java.util.Comparator;



	
public class SortCompare implements Comparator<Double>{

//	@Override
//	public int compare(Double d1, Double d2) {
//		// TODO Auto-generated method stub
//		if(d1 < d2){ 
//			return 1; 
//		}else if(d1 == d2){ 
//			return 0; 
//		}else{ 
//			return -1; 
//		}
//	}

	@Override
	public int compare(Double d1, Double d2) {
		// TODO Auto-generated method stub
		double f1 = d1.doubleValue();
		double f2 = d2.doubleValue();
		if(f1 < f2){ 
			return 1; 
		}else if(f1 == f2){ 
			return 0; 
		}else{ 
			return -1; 
		}
	}
	
}
	
