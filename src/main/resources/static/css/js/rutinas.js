

function previewImage(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var preview = document.getElementById("preview");
            preview.src = e.target.result;
            preview.style.display = "block"; // Muestra la imagen
        };
        reader.readAsDataURL(input.files[0]); // Convierte la imagen a URL
    }
}