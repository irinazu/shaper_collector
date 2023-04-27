package com.example.shaper_collector;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EfficientDistribution {
    BigInteger numberOfQueue;
    Double timeOfTransferOneMb;
    BigDecimal sizeOfQueueMb;
    BigDecimal takeFromSubtask;
    BigDecimal timeOfCheckOneQueueInSecond;
    Integer sizeOfCities;
    BigDecimal sizeOfMatrixMb;
    String url="https://api.wolframalpha.com/v2/query?appid=R659HT-EWEHG9PQP6&input=solve+";
    String format="&format=image";
    int decimalsToConsider = 10;

    public EfficientDistribution(Integer countOfPassengers, Double timeOfTransferOneMb,Double timeOfCheckOneQueueInSecond,Integer sizeOfCities) {
        BigDecimal bigDecimal = BigDecimal.valueOf(timeOfCheckOneQueueInSecond);
        this.timeOfCheckOneQueueInSecond = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
        this.timeOfTransferOneMb=timeOfTransferOneMb;
        this.sizeOfCities=sizeOfCities;
        countOfQueue(countOfPassengers);
        countSizeOfQueueMb(countOfPassengers);
        countTakeFromSubtask();
        countSizeOfMatrixMb();
    }


    public Integer countOfSubtask(){
        String equation="("+numberOfQueue+"/x*"+timeOfCheckOneQueueInSecond+")/(("+numberOfQueue+"/x*"+sizeOfQueueMb+"plus"+sizeOfMatrixMb+")*x*"+timeOfTransferOneMb+")plus(x*"+takeFromSubtask+")=1";
        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.getForObject(url+equation+format, String.class);

        /*final RestTemplate restTemplate = new RestTemplate();
        final String stringPosts = restTemplate.getForObject("https://api.wolframalpha.com/v2/query?appid=R659HT-EWEHG9PQP6&input=solve+(5040/x*0.0061215)/((5040/x*0.00002670288plus0.02929)*x*1.5)plus(x*0.00004005)=1", String.class);
        System.out.println(stringPosts);
        return 0;*/

        /*String str=
                "<?xml version='1.0' encoding='UTF-8'?>\n" +
                        "<queryresult success='true'\n" +
                        "    error='false'\n" +
                        "    xml:space='preserve'\n" +
                        "    numpods='4'\n" +
                        "    datatypes='Solve'\n" +
                        "    timedout=''\n" +
                        "    timedoutpods=''\n" +
                        "    timing='1.594'\n" +
                        "    parsetiming='0.5730000000000001'\n" +
                        "    parsetimedout='false'\n" +
                        "    recalculate=''\n" +
                        "    id='MSP9061hie0687h2b569a7000020039ede13972671'\n" +
                        "    host='https://www6b3.wolframalpha.com'\n" +
                        "    server='12'\n" +
                        "    related='https://www6b3.wolframalpha.com/api/v1/relatedQueries.jsp?id=MSPa9071hie0687h2b569a700002fd8haecd4habfgb822558548030476863'\n" +
                        "    version='2.6'\n" +
                        "    inputstring='solve (120/x*0.0005467000)/((120/x*0.0000104904plus0.0002098083)*x*1.5)plus(x*0.0000157356)=1'>\n" +
                        " <pod title='Input interpretation'\n" +
                        "     scanner='Identity'\n" +
                        "     id='Input'\n" +
                        "     position='100'\n" +
                        "     error='false'\n" +
                        "     numsubpods='1'>\n" +
                        "  <subpod title=''>\n" +
                        "   <img src='https://www6b3.wolframalpha.com/Calculate/MSP/MSP9081hie0687h2b569a700000d3b28h93ihcai25?MSPStoreType=image/gif&amp;s=12'\n" +
                        "       alt='solve (120/x×0.0005467000)/((120/x×0.0000104904 + 0.0002098083) x×1.5) + x×0.0000157356 = 1'\n" +
                        "       title='solve (120/x×0.0005467000)/((120/x×0.0000104904 + 0.0002098083) x×1.5) + x×0.0000157356 = 1'\n" +
                        "       width='546'\n" +
                        "       height='97'\n" +
                        "       type='Grid'\n" +
                        "       themes='1,2,3,4,5,6,7,8,9,10,11,12'\n" +
                        "       colorinvertable='true'\n" +
                        "       contenttype='image/gif' />\n" +
                        "  </subpod>\n" +
                        "  <expressiontypes count='1'>\n" +
                        "   <expressiontype name='Default' />\n" +
                        "  </expressiontypes>\n" +
                        " </pod>\n" +
                        " <pod title='Results'\n" +
                        "     scanner='Solve'\n" +
                        "     id='Result'\n" +
                        "     position='200'\n" +
                        "     error='false'\n" +
                        "     numsubpods='3'\n" +
                        "     primary='true'>\n" +
                        "  <subpod title=''>\n" +
                        "   <img src='https://www6b3.wolframalpha.com/Calculate/MSP/MSP9091hie0687h2b569a7000051fcaa748f7b5ed0?MSPStoreType=image/gif&amp;s=12'\n" +
                        "       alt='x = -17.7444'\n" +
                        "       title='x = -17.7444'\n" +
                        "       width='93'\n" +
                        "       height='19'\n" +
                        "       type='Default'\n" +
                        "       themes='1,2,3,4,5,6,7,8,9,10,11,12'\n" +
                        "       colorinvertable='true'\n" +
                        "       contenttype='image/gif' />\n" +
                        "  </subpod>\n" +
                        "  <subpod title=''>\n" +
                        "   <img src='https://www6b3.wolframalpha.com/Calculate/MSP/MSP9101hie0687h2b569a7000053iegh22e10ig383?MSPStoreType=image/gif&amp;s=12'\n" +
                        "       alt='x = 11.7477'\n" +
                        "       title='x = 11.7477'\n" +
                        "       width='81'\n" +
                        "       height='19'\n" +
                        "       type='Default'\n" +
                        "       themes='1,2,3,4,5,6,7,8,9,10,11,12'\n" +
                        "       colorinvertable='true'\n" +
                        "       contenttype='image/gif' />\n" +
                        "  </subpod>\n" +
                        "  <subpod title=''>\n" +
                        "   <img src='https://www6b3.wolframalpha.com/Calculate/MSP/MSP9111hie0687h2b569a70000100808c74d2d98eh?MSPStoreType=image/gif&amp;s=12'\n" +
                        "       alt='x = 63550.2'\n" +
                        "       title='x = 63550.2'\n" +
                        "       width='83'\n" +
                        "       height='19'\n" +
                        "       type='Default'\n" +
                        "       themes='1,2,3,4,5,6,7,8,9,10,11,12'\n" +
                        "       colorinvertable='true'\n" +
                        "       contenttype='image/gif' />\n" +
                        "  </subpod>\n" +
                        "  <expressiontypes count='3'>\n" +
                        "   <expressiontype name='Default' />\n" +
                        "   <expressiontype name='Default' />\n" +
                        "   <expressiontype name='Default' />\n" +
                        "  </expressiontypes>\n" +
                        "  <states count='1'>\n" +
                        "   <state name='Step-by-step solution'\n" +
                        "       input='Result__Step-by-step solution'\n" +
                        "       stepbystep='true'\n" +
                        "       buttonstyle='StepByStepSolution' />\n" +
                        "  </states>\n" +
                        " </pod>\n" +
                        " <pod title='Plot'\n" +
                        "     scanner='Solve'\n" +
                        "     id='RootPlot'\n" +
                        "     position='300'\n" +
                        "     error='false'\n" +
                        "     numsubpods='1'>\n" +
                        "  <subpod title=''>\n" +
                        "   <img src='https://www6b3.wolframalpha.com/Calculate/MSP/MSP9121hie0687h2b569a700006364b7d6gha03e91?MSPStoreType=image/gif&amp;s=12'\n" +
                        "       alt='Plot'\n" +
                        "       title=''\n" +
                        "       width='600'\n" +
                        "       height='191'\n" +
                        "       type='2DMathPlot_1'\n" +
                        "       themes='1,2,3,4,5,6,7,8,9,10,11,12'\n" +
                        "       colorinvertable='true'\n" +
                        "       contenttype='image/gif' />\n" +
                        "  </subpod>\n" +
                        "  <expressiontypes count='1'>\n" +
                        "   <expressiontype name='Default' />\n" +
                        "  </expressiontypes>\n" +
                        " </pod>\n" +
                        " <pod title='Number line'\n" +
                        "     scanner='Solve'\n" +
                        "     id='NumberLine'\n" +
                        "     position='400'\n" +
                        "     error='false'\n" +
                        "     numsubpods='1'>\n" +
                        "  <subpod title=''>\n" +
                        "   <img src='https://www6b3.wolframalpha.com/Calculate/MSP/MSP9131hie0687h2b569a7000048bg2a56iaad3463?MSPStoreType=image/gif&amp;s=12'\n" +
                        "       alt='Number line'\n" +
                        "       title=''\n" +
                        "       width='340'\n" +
                        "       height='39'\n" +
                        "       type='1DMathPlot_1'\n" +
                        "       themes='1,2,3,4,5,6,7,8,9,10,11,12'\n" +
                        "       colorinvertable='true'\n" +
                        "       contenttype='image/gif' />\n" +
                        "  </subpod>\n" +
                        "  <expressiontypes count='1'>\n" +
                        "   <expressiontype name='Default' />\n" +
                        "  </expressiontypes>\n" +
                        " </pod>\n" +
                        "</queryresult>";*/

        String[] words = str.split("\n");
        List<String> list=new ArrayList<>();
        for(int i=0;i<words.length;i++){
            if(words[i].contains("alt")){
                list.add(words[i]);
            }
        }
        String finishString=list.get(2);
        String[]words_2=finishString.split("=");
        String finishNumber=words_2[2].replace("'","").trim();
        double doubleFinishNumber= Double.parseDouble(finishNumber);
        return (int) Math.round(doubleFinishNumber);
    }

    private void countOfQueue(Integer countOfPassengers){
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= countOfPassengers; i++){
            result = result.multiply(BigInteger.valueOf(i));
        }
        numberOfQueue=result;
    }

    private void countSizeOfQueueMb(Integer countOfPassengers){
        BigDecimal bigDecimal = BigDecimal.valueOf((countOfPassengers * 2 + 1) / 1024.0 / 1024.0);
        sizeOfQueueMb = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
    }

    private void countTakeFromSubtask(){
        BigDecimal bigDecimal = sizeOfQueueMb.multiply(new BigDecimal(timeOfTransferOneMb));;
        takeFromSubtask = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);
    }

    private void countSizeOfMatrixMb(){
        BigDecimal bigDecimal = BigDecimal.valueOf(((sizeOfCities * 2 + 2) * sizeOfCities) / 1024.0 / 1024.0);
        sizeOfMatrixMb = bigDecimal.setScale(decimalsToConsider, BigDecimal.ROUND_HALF_UP);

    }

    @Override
    public String toString() {
        return "EfficientDistribution{" +
                "numberOfQueue=" + numberOfQueue +
                ", timeOfTransferOneMb=" + timeOfTransferOneMb +
                ", sizeOfQueueMb=" + sizeOfQueueMb +
                ", takeFromSubtask=" + takeFromSubtask +
                ", timeOfCheckOneQueueInSecond=" + timeOfCheckOneQueueInSecond +
                ", sizeOfCities=" + sizeOfCities +
                ", sizeOfMatrixMb=" + sizeOfMatrixMb +
                '}';
    }
}
