document.addEventListener('DOMContentLoaded', () => {
    if (!window.userDashboardData) {
        return;
    }

    const chartCanvas = document.getElementById('workoutBreakdownChart');
    if (chartCanvas) {
        const breakdown = window.userDashboardData.workoutBreakdown || {};
        const labels = Object.keys(breakdown);
        const values = Object.values(breakdown);

        new Chart(chartCanvas, {
            type: 'doughnut',
            data: {
                labels: labels.length ? labels : ['No Workouts Logged'],
                datasets: [{
                    data: values.length ? values : [1],
                    backgroundColor: ['#3b82f6', '#10b981', '#f97316', '#6366f1', '#ef4444']
                }]
            },
            options: {
                plugins: {
                    legend: { position: 'bottom' }
                }
            }
        });
    }
});

