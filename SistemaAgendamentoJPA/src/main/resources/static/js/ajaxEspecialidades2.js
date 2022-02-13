function apresentaEspecialidade() {
    var especialidade = document.getElementById("especialidade");
    var nome = especialidade.value;
    // console.log(nome);

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cliente/listarProfissionalEspecialidade/' + nome,
        success: function (result) {
            console.log("RESULTADO: " + result[0]);
            console.log(typeof (result));
            //testando qualquer coisa, colocar tabela?
            var obj = JSON.parse(result);
            console.log("nome: " + obj.nome);
            var s = '<th value="">Selecione</option>';
            for (var i = 0; i < result.length; i++) {
                s += '<option value="' + result[i].id + '">'
                    + result[i].nome
                    + '</option>';

            }
            $('#tabela_especialidade').html(s);
        },
        error: function (request, status, error) {
            alert(request.responseText);
        }
    });
}

