<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="variables">
        <h2 class="jh-entity-heading" data-cy="variablesDetailsHeading">
          <span v-text="$t('proFootballClubApp.variables.detail.title')">Variables</span> {{ variables.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.variables.leagueFirstPlacePrize')">League First Place Prize</span>
          </dt>
          <dd>
            <span>{{ variables.leagueFirstPlacePrize }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.variables.leagueSecondPlacePrize')">League Second Place Prize</span>
          </dt>
          <dd>
            <span>{{ variables.leagueSecondPlacePrize }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="variables.id"
          :to="{ name: 'VariablesEdit', params: { variablesId: variables.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./variables-details.component.ts"></script>
