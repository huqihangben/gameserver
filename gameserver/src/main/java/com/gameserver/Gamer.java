package com.gameserver;
/**
 * 玩家类，包含玩家的局内游戏信息
 * @author mayn
 *
 */
public class Gamer {
	/**
	 * 标识
	 */
	private Long Id;
	/**
	 * 局内位置
	 */
	private Integer pos;
	/**
	 * 本局胡数
	 */
	private Integer huShu;
	/**
	 * 得分
	 */
	private Double score = 0d;
	
	private Gamer(){
		
	}
	
	public static Gamer getInstance(Long id){
		Gamer gamer = new Gamer();
		gamer.setId(id);
		return gamer;
	}
	
	public boolean joinGame(Game game){
		return game.joinMember(this);
	}
	
	public void addScore(Double change){
		/*if(isBanker()){
			change *= 2;
		}*/
		score += change;
	}
	
	public void innerAddScore() {
		addScore((double)huShu);
		
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}
	public Integer getHuShu() {
		return huShu;
	}
	public void setHuShu(Integer huShu) {
		this.huShu = huShu;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}

	public boolean isBanker() {
		
		return getPos().equals(Game.BANKER_POS);
	}

	@Override
	public String toString() {
		return this.getId()+":"+getScore();
	}

	

}
