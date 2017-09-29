package com.feicent.zhang.io.excel;

/**
 * 先定义一个基本的类AwbData
 * @author yzuzhang
 */
public class AwbDto {

    public AwbDto() {
        super();
    }
    
    public AwbDto(String awbNumber, String agent, String datetime) {
        super();
        this.awbNumber = awbNumber;
        this.agent = agent;
        this.datetime = datetime;
    }
    
    /**
     * 运单号
     */
    private String awbNumber;

    /**
     * 代理人
     */
    private String agent;
    
    private String datetime;

    public String getAwbNumber() {
        return awbNumber;
    }

    public void setAwbNumber(String awbNumber) {
        this.awbNumber = awbNumber;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
}
