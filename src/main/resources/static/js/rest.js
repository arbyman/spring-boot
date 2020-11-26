const model = new Proxy({ users: [] }, {
    set: (target, property, value) => {
        target[property] = value;
        viewTable();
    }
})

document.addEventListener("DOMContentLoaded", async () => {
    const response = await fetch("/admin/users");
    if (response.ok) {
        model.users = await response.json();
    } else {
        console.log(`Error http: ${response.status}`);
    }
});

const viewTable = () => {
    const tbody = document.querySelector("#users-table > tbody");
    tbody.innerHTML = "";
    model.users.forEach((user) => {
        const { id, username, lastname, age, email, roles } = user;
        const newRow = tbody.insertRow();
        newRow.insertCell(0).textContent = id;
        newRow.insertCell(1).textContent = username;
        newRow.insertCell(2).textContent = lastname;
        newRow.insertCell(3).textContent = age;
        newRow.insertCell(4).textContent = email;
        newRow.insertCell(5).textContent = roles.map(role => role.role.slice(5)).sort().join(" ");
        const editBtn = document.createElement("button");
        editBtn.classList.add("btn", "btn-info");
        editBtn.dataset.toggle = "modal";
        editBtn.dataset.target = "#edit";
        editBtn.textContent = "Edit";
        editBtn.addEventListener("click", openEditModal(id));
        const deleteBtn = document.createElement("button");
        deleteBtn.classList.add("btn", "btn-danger");
        deleteBtn.dataset.toggle = "modal";
        deleteBtn.dataset.target = "#delete";
        deleteBtn.textContent = "Delete";
        deleteBtn.addEventListener("click", openDeleteModal(id));
        newRow.insertCell(6).append(editBtn);
        newRow.insertCell(7).append(deleteBtn);
    });
};

const getUserById = id => model.users.find(({ id: userId }) => userId === id);

const openDeleteModal = id => () => {
    const { username, lastname, age, email, roles } = getUserById(id);
    const form = document.forms.delete;
    form.id.value = id;
    form.username.value = username;
    form.lastname.value = lastname;
    form.age.value = age;
    form.email.value = email;
    const nameRoles = roles.map(({ role }) => role);
    for (let option of form.roles.options) {
        option.selected = nameRoles.includes(option.value);
    }
    const btn = document.querySelector("button[form='delete-user-form']");
    btn.onclick = deleteUser(id);
};

const openEditModal = id => () => {
    const { username, lastname, age, email, roles } = getUserById(id);
    const form = document.forms.edit;
    form.id.value = id;
    form.username.value = username;
    form.lastname.value = lastname;
    form.age.value = age;
    form.email.value = email;
    const nameRoles = roles.map(({ role }) => role);
    for (let option of form.roles.options) {
        option.selected = nameRoles.includes(option.value);
    }
    const btn = document.querySelector("button[form='edit-user-form']");
    btn.onclick = editUser(id);
};

const deleteUser = id => async (e) => {
    e.preventDefault();
    const { users } = model;
    const response = await fetch(`/admin/users/${id}`, {
        method: "DELETE",
    });
    if (response.ok) {
        model.users = users.filter(({ id: userId }) => userId !== id);
    } else {
        console.log(`Error http: ${response.status}`);
    }
};

const editUser = id => async (e) => {
    e.preventDefault();
    const { users } = model;
    const editUserForm = document.getElementById("edit-user-form");
    const response = await fetch(`/admin/users/${id}`, {
        method: "PUT",
        body: new FormData(editUserForm),
    });
    if (response.ok) {
        const editedUser = await response.json();
        model.users = users.map(user => user.id === id ? editedUser : user);
    } else {
        console.log(`Error http: ${response.status}`);
    }
};

const newUserForm = document.forms.new;
newUserForm.onsubmit = async (e) => {
    const { users } = model;
    e.preventDefault();
    const response = await fetch("admin/users", {
        method: "POST",
        body: new FormData(newUserForm),
    });
    if (response.ok) {
        const user = await response.json();
        model.users = [...users, user];
        newUserForm.reset();
    } else {
        console.log(`Error http: ${response.status}`);
    }
};