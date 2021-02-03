var diff,out,nid,niv,nTir=6,res,str,tir=[],nCal;
    var plq=[1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,25,50,75,100];
    function Ini() {
      (out=document.getElementById('Out')).value=""+plq.length+" plaques: ["+plq+"]";
      nCal=0; str=""; Tir();
    }
    function Tir() {
      out.value+=str;
      if (nCal) out.value+="\n   ==> "+nCal+" appels à Cal()";
      for (var n=0,sel=0; n<nTir; ++n) { // tirage sans remise
        do i=RandInt(plq.length); while (sel&(1<<i)); // plq.length <= 32
        sel|=(1<<i); tir[n]=plq[i];
      }
      res=100+RandInt(900);   // tir=[8,1,2,9,4,1]; res=824;
      out.value+="\n\n"+nTir+" tirages: ["+tir+"] "+res; out.scrollTop=out.scrollHeight;
      str=""; nid=-1; niv=-1; nCal=0; diff=10000000000;
    }
    function Cal(nbr,txt) {
      var n=nbr.length-2,r;
      for (var i=0,a=nbr[i]; i<nbr.length; a=nbr[++i]) {
        var nb=nbr.slice(0);
        nb.splice(i,1); // nbr sans l'élément a d'index i
        for (var j=0,b=nb[j]; j<=n; b=nb[++j]) {
          if (b>=a) { // évite beaucoup de doublons dus à la permutativité
            if ((r=b+a)==res) Sol("+"); else if (n>niv) Recur("+");
            if (a!=b) { // évite b-a=0
              if ((r=b-a)==res) Sol("-"); else if (n>niv) Recur("-");
            }
            if (a>1) { // évite la multiplication et division par 1
              if ((r=b*a)==res) Sol("×"); else if (n>niv) Recur("×");
              if (b%a==0) { // division entière
                if ((r=b/a)==res) Sol("÷"); else if (n>niv) Recur("÷");
              } 
            }
          }
        }
      } ++nCal;
      function Sol(op) { // solution exacte trouvée
        if (n>niv) {niv=n; str="";} // meilleure solution: annulle les antérieures
        str+=txt+b+op+a+"="+r; // ajoute la solution
      }
      function Recur(op) { // niv<0: aucune solution exacte n'est encore trouvée
        if ((niv<0)&&((d=Math.abs(r-res))<=diff)) { // bonne approximation
          if ((d<diff)||(n>nid)) {str=""; diff=d; nid=n;} // meilleure approximation: on élimine les autres
          if (n==nid) str+=txt+b+op+a+"="+r+", différence = "+d; // ajoute l'approximation
        }
        nb[j]=r; Cal(nb,txt+b+op+a+"="+r+", "); nb[j]=b;
      }
    }
    function RandInt(n) {return Math.floor(Math.random()*n);}