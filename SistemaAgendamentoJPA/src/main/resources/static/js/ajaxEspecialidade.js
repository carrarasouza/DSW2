$(document).ready(function() {

		$('#especialidade').on('keyup', function() {
			var especialidade = $(this).val();
			console.log(especialidade)
			//$("especialidades").innerHTML = "";
			var link = /*[[@{/cliente/}]]*/'';
			var autenticado = /*[[ sec:authorize="isAuthenticated()"]]*/'';
			console.log("link = "  +link);
			$.ajax({
				type : 'GET',
				url : 'http://localhost:8080/listar/especialidade',
				data : {tipo : especialidade},
				success : function(result) {
					var divBody = document.getElementById("especialidades");
					//divBody.innerHTML = "";
					var aux = document.createElement("div");
					var s = '';
					//s += '<tbody id="especialidades" name="especialidades">';
					console.log(result)
					console.log(result.length);
					for (var i = 0; i < result.length; i++) {
						var indice = result[i].split("-");
						var id = indice[0];
						var nome = indice[1];
						var email = indice[2];
						var auxEspecialidade = indice[3];
						var curriculo = indice[4];
                                
                                      
						s += '<tr>';
						s += '   <td> ' + nome + ' </td>';
						s += '   <td> ' + email + ' </td>'; 
						s += '   <td> ' + auxEspecialidade + ' </td>';
						s += '   <td colspan="1">';
                        s += '       	<a class="btn btn-info btn-sm" href="/upload/'+ curriculo +'" role="button">';
                        s += '     		   <span class="oi oi-arrow-circle-bottom" th:title="#{profissional.curriculo.arquivo}" aria-hidden="true"> </span>';
                        s += '          </a>'; 
						s += '   <td '+autenticado +' colspan="2">';
						s += '     <a class="btn btn-info btn-sm" href="cliente/cadastrarConsulta/' +id+'"  role="button"> ';
						s += '        <span class="oi oi-calendar" th:title="#{link.consulta.label}" aria-hidden="true"> </span>';
						s += '      </a>';
						s += '    </td>';
						s += '</tr>';
					}					
					//s += '</tbody>';
					//aux.innerHTML = s;
					console.log(s);
					//divBody.innerHTML = s;
					$('#especialidades').html(s);
				},
				error: function (request, status, error) {
				       alert(request.responseText);
				}
			});
		})
		
		$('#especialidade').keyup();
	});