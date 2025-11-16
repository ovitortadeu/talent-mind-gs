#!/bin/bash

# ===================================================================
# SCRIPT DE DEPLOY PARA O CHALLENGE MOTTU - DEVOPS
# ===================================================================

# --- CONFIGURAÇÕES ---
export RESOURCE_GROUP_NAME="rg-mottu-rm559105" 
export LOCATION="brazilsouth"
export GITHUB_REPO_URL="ovitortadeu/challenge-devops-mottu" 

# --- VARIÁVEIS DO PROJETO ---
export APP_SERVICE_PLAN="plan-mottu-app"
export WEBAPP_NAME="app-mottu-rm559105"
export SQL_SERVER_NAME="sql-server-mottu-rm559105" 
export SQL_DATABASE_NAME="mottu-db"
export SQL_ADMIN_USER="mottuadmin" 
export SQL_ADMIN_PASSWORD="FiapDevops@2025" 
export APP_INSIGHTS_NAME="ai-mottu-app"
export BRANCH="main" 

echo ">>> Iniciando o processo de deploy na Azure..."
echo ">>> Grupo de Recursos: $RESOURCE_GROUP_NAME"
echo ">>> Região: $LOCATION"

# 1. Criar Grupo de Recursos
echo "--> [1/9] Criando o Grupo de Recursos..."
az group create --name $RESOURCE_GROUP_NAME --location "$LOCATION" -o table

# 2. Criar Servidor SQL
echo "--> [2/9] Criando o Servidor SQL..."
az sql server create \
    --name $SQL_SERVER_NAME \
    --resource-group $RESOURCE_GROUP_NAME \
    --location "$LOCATION" \
    --admin-user $SQL_ADMIN_USER \
    --admin-password $SQL_ADMIN_PASSWORD -o table

# 3. Configurar Firewall do Servidor SQL
echo "--> [3/9] Configurando o Firewall do SQL Server para permitir acesso de serviços da Azure..."
az sql server firewall-rule create \
    --name "AllowAzureServices" \
    --resource-group $RESOURCE_GROUP_NAME \
    --server $SQL_SERVER_NAME \
    --start-ip-address "0.0.0.0" \
    --end-ip-address "0.0.0.0" -o table

# 4. Criar Banco de Dados SQL
echo "--> [4/9] Criando o Banco de Dados..."
az sql db create \
    --name $SQL_DATABASE_NAME \
    --resource-group $RESOURCE_GROUP_NAME \
    --server $SQL_SERVER_NAME \
    --service-objective S0 -o table

# 5. Criar Application Insights
echo "--> [5/9] Criando o Application Insights..."
az monitor app-insights component create \
  --app "$APP_INSIGHTS_NAME" \
  --location "$LOCATION" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --application-type web -o table

# 6. Criar Plano de Serviço do App
echo "--> [6/9] Criando o Plano de Serviço..."
az appservice plan create \
  --name $APP_SERVICE_PLAN \
  --resource-group $RESOURCE_GROUP_NAME \
  --location "$LOCATION" \
  --sku F1 \
  --is-linux -o table

# 7. Criar o App Service (Web App)
echo "--> [7/9] Criando o App Service..."
az webapp create \
  --name "$WEBAPP_NAME" \
  --resource-group $RESOURCE_GROUP_NAME \
  --plan "$APP_SERVICE_PLAN" \
  --runtime "JAVA:21-java21" -o table

# 8. Configurar as Variáveis de Ambiente no App Service
echo "--> [8/9] Configurando as Variáveis de Ambiente..."
# String de conexão JDBC para Azure SQL
JDBC_URL="jdbc:sqlserver://$SQL_SERVER_NAME.database.windows.net:1433;database=$SQL_DATABASE_NAME;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
# String de conexão do App Insights
APPINSIGHTS_CONNECTION_STRING=$(az monitor app-insights component show --app "$APP_INSIGHTS_NAME" --resource-group "$RESOURCE_GROUP_NAME" --query connectionString -o tsv)

az webapp config appsettings set \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --settings \
    APPLICATIONINSIGHTS_CONNECTION_STRING="$APPINSIGHTS_CONNECTION_STRING" \
    ApplicationInsightsAgent_EXTENSION_VERSION="~3" \
    SPRING_DATASOURCE_URL="$JDBC_URL" \
    SPRING_DATASOURCE_USERNAME="$SQL_ADMIN_USER" \
    SPRING_DATASOURCE_PASSWORD="$SQL_ADMIN_PASSWORD" -o table

# 9. Configurar o CI/CD com GitHub Actions
echo "--> [9/9] Configurando o pipeline de CI/CD com GitHub Actions..."
az webapp deployment github-actions add \
  --repo "$GITHUB_REPO_URL" \
  --branch $BRANCH \
  --name "$WEBAPP_NAME" \
  --resource-group $RESOURCE_GROUP_NAME \
  --login-with-github

echo ""
echo ">>> DEPLOY CONCLUÍDO! <<<"
echo "Acesse seu repositório GitHub ($GITHUB_REPO_URL) para configurar as secrets e acompanhar o workflow do GitHub Actions."
echo "URL do seu App Service: http://$WEBAPP_NAME.azurewebsites.net"
