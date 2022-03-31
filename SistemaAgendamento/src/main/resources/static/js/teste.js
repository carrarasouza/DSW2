
var input_form = document.getElementById("especialidade");
input_form.addEventListener("keyup", buscarEspecialidade);

function buscarEspecialidade(){
	var autorizado = document.getElementById('consultaAutorizada');
	
	let xhr = new XMLHttpRequest();
	if(input_form.value != ""){
		let url = "http://localhost:8080/listar/especialidade/"+input_form.value;
		xhr.open('GET', url, true);
		xhr.onreadystatechange = function(){
			if(xhr.readyState == XMLHttpRequest.DONE){
				let profissionais = xhr.responseText.replace("[", "");
				profissionais = profissionais.replace("]", "");
				console.log(profissionais);
				var divTableBody = document.getElementById("especialidades");
				if(profissionais.length > 0){
					let profissionaisArray = profissionais.split(",");
					var s = "";
					for (let i in profissionaisArray) {
						var indice = profissionaisArray[i].split("-");
						let profissional = new Profissional(indice[0], indice[1], indice[2], indice[3], indice[4]);
						
						
						s += '<tr>';
						s += '   <td class="nome"> ' + profissional.nome + ' </td>';
						s += '   <td class="email"> ' + profissional.email + ' </td>'; 
						s += '   <td class="especialidade"> ' + profissional.auxEspecialidade + ' </td>';
						s += '   <td colspan="1" class="curriculo">';
		                s += '       	<a href="' + link + '' + profissional.curriculo + '">' + profCurriculo;
		                s += '       </a>';
		                s += '    </td>'; 
		                if(autorizado != null){
							s += '   <td colspan="2" class="consulta" id="consultaAutorizada">';
							s += '     <a th:href="aaaaaaa"> ';
							s += '        <span>' + consultaLabel + '</span>';
							s += '      </a>';
							s += '    </td>';
						}
						s += '</tr>';
						
						divTableBody.innerHTML = s;
					}
				}
				else{
					divTableBody.style.display = "none";
					const addSpan = document.createElement("span");
					const textNode = document.createTextNode("Nenhum profissional foi encontrado");
					addSpan.appendChild(textNode);
					document.getElementsByClassName("noResults")[0].appendChild(addSpan);
				}
			}
		}
		xhr.send();
	}
}

class Profissional{
	constructor(id, nome, email, auxEspecialidade, curriculo){
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.auxEspecialidade = auxEspecialidade;
		this.curriculo = curriculo;
	}
}

