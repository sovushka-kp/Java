let file;
let format;

async function uploadFile() {
    try {
        file = document.getElementById('upload').files[0];
        format = file.name.split('.').pop();
        document.getElementById('img').src = "http://img0.joyreactor.com/pics/post/full/gif-dickbutt-1806864.gif";

        if (format === 'png'
            || format === 'bmp'
            || format === 'jpg'
            || format === 'jpeg') {

            const formData = new FormData();
            formData.append('file', file);

            const response = await fetch(document.location.origin + "/Ucode_Pixelizator_war_exploded/corrupted", {
                method: 'POST',
                body: formData
            });
            if (response.status === 200) {
                document.getElementById('img').src = URL.createObjectURL(file);
                document.getElementById('size').textContent = "File size: " + conv_size(file.size);
                document.getElementById('name').textContent = "File name: " + file.name;
                onElems(true);
            } else {
                document.getElementById('img').src = "https://i.postimg.cc/sxBQw6f9/image.png";
                onElems(false);
            }
        } else {
            document.getElementById('img').src = 'https://i.ibb.co/3dMCNQN/image-1.png';
            onElems(false);
        }
    } catch (error) {
        file = null;
        document.getElementById('img').src = '#';
        onElems(false);
        document.getElementById('upload').removeAttribute("disabled");
    }
}

async function pixelate() {
    const formData = new FormData();
    formData.append('file', file);
    const range = document.getElementById('range').value;

    try {
        document.getElementById('img').src = "http://img0.joyreactor.com/pics/post/full/gif-dickbutt-1806864.gif";
        //document.getElementById('upload').disabled = true;
        onElems(false);
        const response = await fetch(document.location.origin + "/Ucode_Pixelizator_war_exploded/pixel", {
            method: 'POST',
            headers : {
                'format' : format,
                'range' : range
            },
            body: formData
        });
        const blob = await response.blob();
        const reader = new FileReader();
        reader.onloadend = function() { document.getElementById('img').src = reader.result; }
        reader.readAsDataURL(blob);
        document.getElementById('size').textContent = "File size: " + conv_size(blob.size);
        document.getElementById('name').textContent = "File name: " + file.name;
        onElems(true);
    } catch (error) {
        console.error('Ошибка:', error);
        onElems(false);
    }
}

function download(format) {
    var link = document.createElement('a');
    link.setAttribute('href', document.getElementById('img').src);
    link.setAttribute('download','pixelate.' + format);
    link.click()
}

function onElems(flag) {
    if (flag == false) {
        document.getElementById('name').textContent = "";
        document.getElementById('size').textContent = "";
        document.getElementById('range').disabled = true;
        document.getElementById('number').disabled = true;
        document.getElementById('pixelate').disabled = true;
        document.getElementById('png').disabled = true;
        document.getElementById('bmp').disabled = true;
        document.getElementById('jpeg').disabled = true;
        document.getElementById('jpg').disabled = true;
    }
    if (flag == true) {
        document.getElementById('range').removeAttribute("disabled");
        document.getElementById('number').removeAttribute("disabled");
        document.getElementById('pixelate').removeAttribute("disabled");
        document.getElementById('png').removeAttribute("disabled");
        document.getElementById('bmp').removeAttribute("disabled");
        document.getElementById('jpeg').removeAttribute("disabled");
        document.getElementById('jpg').removeAttribute("disabled");
    }
}

function conv_size(b) {
    fsizekb = b / 1024;
    fsizemb = fsizekb / 1024;
    fsizegb = fsizemb / 1024;
    fsizetb = fsizegb / 1024;

    if (fsizekb <= 1024) {
        fsize = fsizekb.toFixed(3) + ' Кб';
    } else if (fsizekb >= 1024 && fsizemb <= 1024) {
        fsize = fsizemb.toFixed(3) + ' Мб';
    } else if (fsizemb >= 1024 && fsizegb <= 1024) {
        fsize = fsizegb.toFixed(3) + ' Гб';
    } else {
        fsize = fsizetb.toFixed(3) + ' Тб';
    }

    return fsize;
}




