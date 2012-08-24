<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Load content while scrolling - Infinite Scroll with Java and JQuery</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <style type="text/css">
            body{
                font-family:Arial;
                font-size:.93em;
            }
            #content-box{
                background-color:#FAFAFA;
                border:2px solid #888;
                height:300px;
                overflow:scroll;
                padding:4px;
                width:500px;
            }
            #content-box p{
                border:1px solid #EEE;
                background-color:#F0F0F0;
                padding:3px;
            }
            #content-box p span{
                color:#00F;
                display:block;
                font:bold 21px Arial;
                float:left;
                margin-right:10px;
            }
        </style>
        <script type="text/javascript">
            $(document).ready(function(){
                //                alert('ready');
                $contentLoadTriggered = false;
                $("#content-box").scroll(function(){
                    if($("#content-box").scrollTop() >= ($("#content-wrapper").height() - $("#content-box").height()) && $contentLoadTriggered == false)
                    {
                        var myarr = [];
                        $('.ololo').each(function(){
                            myarr[myarr.length] = $(this).attr('id');
                            alert($(this).attr('id'));
                        });
                        //                        });
                        //                        alert(myarr.);

                        $contentLoadTriggered = true;
                        $.get("LazyServlet", function(data){
                            myString = $("<span />").html(data).text();
                            $("#content-wrapper").append(myString);
                            $contentLoadTriggered = false;
                        });
                    }

                });
            });
        </script>
    </head>
    <body>
        <h1>Demo page: Infinite Scroll with Java and JQuery</h1>
        <p>This page is a demo for loading new content while scrolling.</p>
        <p style="margin:20px 0;background-color:#EFEFEF;border:1px solid #EEE;padding:3px;">
            Credits: Veera Sundar | <a href="http://veerasundar.com">veerasundar.com</a> | <a href="http://twitter.com/vraa">@vraa</a>
        </p>
        <div id="content-box">
            <div id="content-wrapper">
                <p class="ololo" id="1"><span >1</span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ornare facilisis mollis. Etiam non sem massa, a gravida nunc. Mauris lectus augue, posuere at viverra sed, dignissim sed libero. </p>
                <p class="ololo"  id="2"><span >2</span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ornare facilisis mollis. Etiam non sem massa, a gravida nunc. Mauris lectus augue, posuere at viverra sed, dignissim sed libero. </p>
                <p class="ololo"  id="3"><span >3</span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ornare facilisis mollis. Etiam non sem massa, a gravida nunc. Mauris lectus augue, posuere at viverra sed, dignissim sed libero. </p>
                <p class="ololo"  id="4"><span >4</span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ornare facilisis mollis. Etiam non sem massa, a gravida nunc. Mauris lectus augue, posuere at viverra sed, dignissim sed libero. </p>
                <p class="ololo"  id="5"><span >5</span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ornare facilisis mollis. Etiam non sem massa, a gravida nunc. Mauris lectus augue, posuere at viverra sed, dignissim sed libero. </p>
            </div>
        </div>
    </body>
</html>