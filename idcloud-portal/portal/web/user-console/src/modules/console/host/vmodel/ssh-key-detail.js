define(['avalon'] , function(){
    var detailSSH = avalon.define({
        $id:'detailSSH',
        data:null,
        publicKey:"",
        publicKeyFun : function(){
            var publicKey = detailSSH.data.publicKey;
            var attr = publicKey.split(' ');
            detailSSH.publicKey += attr[0];
            detailSSH.publicKey += " ";
            for(var i = 1;i < attr[1].length; i++){
                detailSSH.publicKey += attr[1][i];
                if(i % 48 == 0){
                    detailSSH.publicKey += " ";
                }
            }
        }
    });
    return detailSSH;

});

