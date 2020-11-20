const editHandler = async (e) => {
    const href = e.currentTarget.getAttribute("href");
    const response = await fetch(href);
    if (response.ok) {
        const user = await response.json();
        const { id, username, lastname, age, email } = user;
        document.getElementById("edit-id").value = id;
        document.getElementById("edit-username").value = username;
        document.getElementById("edit-lastname").value = lastname;
        document.getElementById("edit-age").value = age;
        document.getElementById("edit-email").value = email;
        const form = document.getElementById("edit-user");
        form.setAttribute("action", `/admin/edit/${id}`);
    } else {
        console.log(`Error http: ${response.status}`);
    }
}

const deleteHandler = async (e) => {
    const href = e.currentTarget.getAttribute("href");
    const response = await fetch(href);
    if (response.ok) {
        const user = await response.json();
        const { id, username, lastname, age, email } = user;
        const form = document.getElementById("delete-user");
        form.setAttribute("action", `/admin/delete/${id}`);
        document.getElementById("delete-id").value = id;
        document.getElementById("delete-username").value = username;
        document.getElementById("delete-lastname").value = lastname;
        document.getElementById("delete-age").value = age;
        document.getElementById("delete-email").value = email;
    } else {
        console.log(`Error http: ${response.status}`);
    }
}

const editBtns = document.querySelectorAll(".btn-edit");
const deleteBtns = document.querySelectorAll(".btn-delete");

editBtns.forEach(btn => {
    btn.addEventListener("click", editHandler);
});

deleteBtns.forEach(btn => {
    btn.addEventListener("click", deleteHandler);
});
