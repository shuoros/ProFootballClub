<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.publicChat.home.createOrEditLabel"
          data-cy="PublicChatCreateUpdateHeading"
          v-text="$t('proFootballClubApp.publicChat.home.createOrEditLabel')"
        >
          Create or edit a PublicChat
        </h2>
        <div>
          <div class="form-group" v-if="publicChat.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="publicChat.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.publicChat.from')" for="public-chat-from">From</label>
            <select class="form-control" id="public-chat-from" data-cy="from" name="from" v-model="publicChat.from">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="publicChat.from && teamOption.id === publicChat.from.id ? publicChat.from : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.publicChat.tu')" for="public-chat-tu">Tu</label>
            <select class="form-control" id="public-chat-tu" data-cy="tu" name="tu" v-model="publicChat.tu">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="publicChat.tu && teamOption.id === publicChat.tu.id ? publicChat.tu : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.publicChat.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./public-chat-update.component.ts"></script>
