function menu(){
	let tag = document.getElementsByTagName('aside')[0];
	if(tag.style.display === 'none'){
		tag.style.opacity = '1';
		tag.style.transition = 'opacity 1s';
		tag.style.display = 'block';
		
	}
	else{
		tag.style.opacity = '0';
		tag.style.transition = 'opacity 1s';
		tag.style.display = 'none';
		
	}
}

