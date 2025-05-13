function showSpinner(show = true) {
    const spinner = document.getElementById('spinner');
    if (spinner) {
        spinner.style.display = show ? 'inline-block' : 'none';
    }
}

function showGlobalAlert(message, type = 'info', timeout = 5000) {
    const alertBox = document.getElementById('globalAlert');
    const alertMessage = document.getElementById('globalAlertMessage');

    if (!alertBox || !alertMessage) return;

    alertBox.className = `alert alert-${type} alert-dismissible fade show mx-3 mt-3`;
    alertMessage.textContent = message;
    alertBox.classList.remove('d-none');

    // Auto-hide after timeout
    setTimeout(() => {
        alertBox.classList.remove('show'); // fade out
        setTimeout(() => alertBox.classList.add('d-none'), 200); // hide after fade
    }, timeout);
}

function hideGlobalAlert() {
    document.getElementById('globalAlert')?.classList.add('d-none');
}

document.addEventListener('DOMContentLoaded', function () {
    document.body.addEventListener('click', function (e) {
        const button = e.target;
        if (!button || button.dataset.action !== 'sync') return;

        const url = button.dataset.url;
        const method = button.dataset.method || 'POST';
        const paramId = button.dataset.paramId;

        let body = null;

        if (paramId) {
            const paramValue = document.getElementById(paramId)?.value;
            body = `${paramId}=${encodeURIComponent(paramValue)}`;
        }

        showSpinner(true);
        button.disabled = true;

        fetch(url, {
            method: method,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: body
        })
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                showGlobalAlert(`Sync completed: ${JSON.stringify(data)}`, 'success');
            })
            .catch(() => {
                showGlobalAlert('Sync failed. Please try again.', 'danger');
            })
            .finally(() => {
                showSpinner(false);
                button.disabled = false;
            });
    });
});

