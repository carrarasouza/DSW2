var xmlHttp;


function getEspecialidades() {
    var especialidade = document.getElementById("especialidade");
    var nome = especialidade.value;
    console.log(nome);
    if (typeof XMLHttpRequest !== "undefined") {
        xmlHttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    if (xmlHttp === null) {
        alert("Browser does not support XMLHTTP Request");
        return;
    }

    var url = "listarProfissionalEspecialidade/";
    url += nome;
    xmlHttp.open("GET", url, true);
    xmlHttp.onreadystatechange = atualizaTabelaProfissionais;
    xmlHttp.send(null);
}

function atualizaTabelaProfissionais() {
    if (xmlHttp.readyState === 4 || xmlHttp.readyState === "complete") {
        console.log(xmlHttp.responseText);
        var profissionais = JSON.parse(xmlHttp.responseText);
        console.log(profissionais);
        // CRIA UMA TABELA DINAMICA
        var contextPath1 = window.location.host + "/";
        console.log(contextPath1);
        var contextPath2 = window.location.pathname.split("/")[1];
        var contextPath = contextPath2;
        console.log(contextPath);

        var table = document.createElement("table");
        table.border = "1";
        table.style.border = "1px solid black";
        table.style.width = "400px";

        // CRIA LINHA TABELA (LINHA CABECALHO).
        table.createCaption("Lista de Profissionais");
        var tr = table.insertRow(-1);

        // CRIA COLUNA NA LINHA DE CABECALHO
        var thNome = document.createElement('th');
        thNome.innerHTML = 'Nome';
        thNome.style.width = "20%";
        tr.appendChild(thNome);

        // CRIA COLUNA NA LINHA DE CABECALHO
        var thEmail = document.createElement('th');
        thEmail.innerHTML = 'Email';
        thEmail.style.width = "60%";
        tr.appendChild(thEmail);

        // CRIA COLUNA NA LINHA DE CABECALHO
        var thEspecialidade = document.createElement('th');
        thEspecialidade.innerHTML = 'Especialidade';
        thEspecialidade.style.width = "10%";
        tr.appendChild(thEspecialidade);

        var thCurriculo = document.createElement('th');
        thCurriculo.innerHTML = 'Curriculo';
        thCurriculo.style.width = "10%";
        tr.appendChild(thCurriculo);


        // CRIA DEMAIS LINHAS COM OS VALORES

        for (var i = 0; i < profissionais.length; i++) {

            // CRIA NOVA LINHA
            tr = table.insertRow(-1);


            var nome = profissionais[i].nome;
            var email = profissionais[i].email;
            var especialidade = profissionais[i].especialidade;
            var curriculo = profissionais[i].curriculo;
            var id = profissionais[i].id;
            // CRIA COLUNA 1 NA LINHA

            var col1 = tr.insertCell(-1);
            col1.style.textAlign = "center";
            col1.innerHTML = nome;

            // CRIA COLUNA 2 NA LINHA

            var col2 = tr.insertCell(-1);
            col2.style.textAlign = "center";
            col2.innerHTML = email;

            // CRIA COLUNA 3 NA LINHA

            var col3 = tr.insertCell(-1);
            col3.style.textAlign = "center";
            col3.innerHTML = especialidade;

            // CRIA COLUNA 4 NA LINHA
            //var a = document.createElement('a');
            //a.href = "/<%=request.getContextPath().replace("/", "")%>/curriculo/${profissional.id}_curriculo";

            var col4 = tr.insertCell(-1);
            col4.style.textAlign = "center";
            col4.innerHTML = "<a href=\"/" + contextPath + "/curriculo/" + id + "_curriculo\" target=\"_blank\">" + "Currículo" + "</a>";
        }
        var divContainer = document.getElementById("profissionais");
        divContainer.innerHTML = "";

        divContainer.appendChild(table);
    }
}
