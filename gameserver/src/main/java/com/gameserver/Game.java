package com.gameserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 局内游戏类
 * 
 * 
 * 问题：1.没考虑线程安全问题。
 * 2.有些方法逻辑未分得很开。
 * 3.没有详尽测试，可能有bug
 * 4.边界条件不是很清楚，需求也没有很清楚
 * 
 * 
 * 
 * @author mayn
 *
 */
public class Game {
	
	public static final int BANKER_POS = 0;

	/**
	 * 最大总人数
	 */
	private Integer allNum = 4;
	/**
	 * 参与游戏的玩家
	 */
	private Gamer[] gamers = new Gamer[allNum];
	/**
	 * 已满的位置
	 */
	private boolean[] fullPos =  new boolean[allNum];
	
	private Integer winnerPos;

	/**
	 * 分牌。。。。TODO 具体逻辑略过
	 */
	public void startGame(){
		
	}
	/**
	 * 清空所有一局数据
	 */
	public void endGame(){
		
	}
	
	/**
	 * 记录结果分
	 * @param gamer
	 * @param score
	 */
	private void recordResult(Gamer gamer, Double score){
		
	}
	
	public void recordResults(Double[] scores){
		
	}
	/**
	 * 暂时不分开来 计算并记录成绩
	 */
	public void calculateAndRecordScores(){
		
		Gamer winner = gamers[winnerPos];
		//winner.innerAddScore();
		List<Gamer> list = new ArrayList<>();
		for(Gamer gamer:gamers){
			if(gamer != null && !gamer.getPos().equals(winnerPos)){
				Integer winnerHuShu = winner.getHuShu();
				Double score = huShuToScore(winnerHuShu,winner,gamer);
				winner.addScore(score);
				gamer.addScore(-score);
				//混一起了，TODO 到时候可能需要分开来
				list.add(gamer);
			}
		}
		Queue<Gamer> queue =new PriorityQueue<>((gamer1,gamer2)->{
			
			if(gamer1 == null){
				return 1;
			}
			if(gamer2 == null){
				return -1;
			}
			
			return gamer2.getHuShu() - gamer1.getHuShu();
			
		});
		
		queue.addAll(list);
		while(!queue.isEmpty()){
			
			Gamer top = queue.poll();
			Integer winnerHuShu = top.getHuShu();
			//Double score = huShuToScore(winnerHuShu);
			
			
			for(Gamer g : queue){
				Integer dHuShu = winnerHuShu - g.getHuShu();
				Double score = huShuToScore(dHuShu,top,g);
				top.addScore(score);
				g.addScore(-score);
			}
		}
		
		
	}
	
	private Double huShuToScore(Integer winnerHuShu,Gamer gamerTo,Gamer gamerFrom) {
		if(gamerTo.isBanker() || gamerFrom.isBanker()){
			return (double)winnerHuShu*2;
		}
		return (double)winnerHuShu;
	}
	public void recordHushu(Gamer gamer, Integer huShu){
		Gamer tmp;
		if((tmp = gamers[gamer.getPos()])!= null){
			if(tmp.equals(gamer)){
				gamer.setHuShu(huShu);
			}else{
				// TODO 错误处理
			}
		}
	}
	
	public void recordWinnerPos(Integer pos){
		this.winnerPos = pos;
	}

	public boolean joinMember(Gamer gamer) {
		
		/**
		 * 暂定加入第一个空位
		 */
		for(int i = 0;i < fullPos.length;i ++){
			if(!fullPos[i]){
				fullPos[i] = true;
				gamer.setPos(i);
				gamers[i] = gamer;
				return true;
			}
		}
		
		return false;
		
	} 
	
	public static void main(String[] args) {
		Gamer gamer1 = Gamer.getInstance(1l);
		Gamer gamer2 = Gamer.getInstance(2l);
		Gamer gamer3 = Gamer.getInstance(3l);
		Gamer gamer4 = Gamer.getInstance(4l);
		
		Game game = new Game();
		game.joinMember(gamer1);
		game.joinMember(gamer2);
		game.joinMember(gamer3);
		game.joinMember(gamer4);
		
		game.recordHushu(gamer1, 10);
		game.recordHushu(gamer2, 0);
		game.recordHushu(gamer3, 20);
		game.recordHushu(gamer4, 5);
		
		game.recordWinnerPos(2);
		
		game.calculateAndRecordScores();
		
		System.out.println(Arrays.toString(game.gamers));
		
	}
}
